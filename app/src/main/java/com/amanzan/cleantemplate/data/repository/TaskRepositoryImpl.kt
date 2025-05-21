package com.amanzan.cleantemplate.data.repository

import com.amanzan.cleantemplate.data.local.database.dao.TaskDao
import com.amanzan.cleantemplate.data.local.database.entity.TaskEntity
import com.amanzan.cleantemplate.domain.repository.TaskRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class TaskRepositoryImpl @Inject constructor(
    private val taskDao: TaskDao
) : TaskRepository {

    override val tasks: Flow<List<String>> =
        taskDao.getTasks().map { items -> items.map { it.name } }

    override suspend fun add(name: String) {
        taskDao.insertTask(TaskEntity(name = name))
    }
}