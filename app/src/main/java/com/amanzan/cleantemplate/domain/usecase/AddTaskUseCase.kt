package com.amanzan.cleantemplate.domain.usecase

import com.amanzan.cleantemplate.domain.model.Task
import com.amanzan.cleantemplate.domain.repository.TaskRepository
import java.util.UUID
import javax.inject.Inject

class AddTaskUseCase @Inject constructor(
    private val repository: TaskRepository
) {
    suspend operator fun invoke(name: String) {
        val task = Task(UUID.randomUUID().toString(), name)
        repository.addTask(task)
    }
}