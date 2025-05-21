package com.amanzan.cleantemplate.data.local.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.amanzan.cleantemplate.data.local.database.entity.SyncStatus
import com.amanzan.cleantemplate.data.local.database.entity.TaskEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {
    @Query("SELECT * FROM task ORDER BY uid DESC")
    fun getAllTasks(): Flow<List<TaskEntity>>

    @Query("SELECT * FROM task WHERE syncStatus = :status")
    suspend fun getTasksByStatus(status: SyncStatus): List<TaskEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTasks(tasks: List<TaskEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTask(task: TaskEntity)

    @Query("UPDATE task SET syncStatus = :status WHERE uid = :taskId")
    suspend fun updateTaskSyncStatus(taskId: String, status: SyncStatus)
}