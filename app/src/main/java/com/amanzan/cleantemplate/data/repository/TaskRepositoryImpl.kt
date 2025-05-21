package com.amanzan.cleantemplate.data.repository

import android.util.Log
import com.amanzan.cleantemplate.data.local.database.dao.TaskDao
import com.amanzan.cleantemplate.data.local.database.entity.TaskEntity
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
            val created = api.addTask(task.toDto())
            dao.insertTask(created.toEntity())
        } catch (e: Exception) {
            // Optionally add to a "pending sync" table
            dao.insertTask(task.toEntity())
        }
    }

    override suspend fun syncFromRemote() {
        try {
            val remoteTasks = api.getTasks().map { it.toDomain() }
            dao.insertTasks(remoteTasks.map { it.toEntity() })
        } catch (e: Exception) {
            Log.e("TaskRepositoryImpl", "Sync failed", e)
            throw e // or handle gracefully
        }
    }
}