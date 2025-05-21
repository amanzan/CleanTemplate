package com.amanzan.cleantemplate.domain.repository

import com.amanzan.cleantemplate.domain.model.Task
import kotlinx.coroutines.flow.Flow

interface TaskRepository {
    fun getTasks(): Flow<List<Task>>
    suspend fun addTask(task: Task)
    suspend fun syncFromRemote()
}


