package com.amanzan.cleantemplate.data.local.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.amanzan.cleantemplate.data.local.database.entity.TaskEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {
    @Query("SELECT * FROM task ORDER BY uid DESC LIMIT 10")
    fun getTasks(): Flow<List<TaskEntity>>

    @Insert
    suspend fun insertTask(item: TaskEntity)
}