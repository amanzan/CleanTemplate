package com.amanzan.cleantemplate.data.repository

import android.util.Log
import com.amanzan.cleantemplate.data.local.database.dao.TaskDao
import com.amanzan.cleantemplate.data.local.database.entity.SyncStatus
import com.amanzan.cleantemplate.data.local.database.entity.toDomain
import com.amanzan.cleantemplate.data.local.database.entity.toEntity
import com.amanzan.cleantemplate.data.remote.api.TaskApi
import com.amanzan.cleantemplate.data.remote.dto.toDomain
import com.amanzan.cleantemplate.data.remote.dto.toDto
import com.amanzan.cleantemplate.data.remote.dto.toEntity
import com.amanzan.cleantemplate.domain.model.Task
import com.amanzan.cleantemplate.domain.repository.TaskRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class TaskRepositoryImpl @Inject constructor(
    private val api: TaskApi,
    private val dao: TaskDao
) : TaskRepository {

    override fun getTasks(): Flow<List<Task>> {
        return dao.getAllTasks().map { list -> list.map { it.toDomain() } }
    }

    override suspend fun addTask(task: Task) {
        try {
            // First save locally
            dao.insertTask(task.toEntity().copy(syncStatus = SyncStatus.PENDING))
            
            // Then try to sync with server
            val created = api.addTask(task.toDto())
            // Update local with server version and mark as synced
            dao.insertTask(created.toEntity().copy(syncStatus = SyncStatus.SYNCED))
        } catch (e: Exception) {
            // Keep the local version with PENDING status
            // It will be synced later
            Log.e("TaskRepositoryImpl", "Failed to sync new task", e)
        }
    }

    override suspend fun syncFromRemote() {
        try {
            // 1. Get all pending local changes
            val pendingTasks = dao.getTasksByStatus(SyncStatus.PENDING)
            
            // 2. Try to sync pending changes first
            pendingTasks.forEach { pendingTask ->
                try {
                    val synced = api.addTask(pendingTask.toDomain().toDto())
                    dao.insertTask(synced.toDomain().toEntity().copy(syncStatus = SyncStatus.SYNCED))
                } catch (e: Exception) {
                    Log.e("TaskRepositoryImpl", "Failed to sync pending task ${pendingTask.uid}", e)
                    // Keep as pending for next sync
                }
            }

            // 3. Get remote tasks and update local
            val remoteTasks = api.getTasks()
            dao.insertTasks(remoteTasks.map { 
                it.toDomain().toEntity().copy(syncStatus = SyncStatus.SYNCED)
            })
        } catch (e: Exception) {
            Log.e("TaskRepositoryImpl", "Sync failed", e)
            throw e
        }
    }
}