package com.amanzan.cleantemplate.domain.usecase

import com.amanzan.cleantemplate.domain.repository.TaskRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetTasksUseCase @Inject constructor(
    private val repository: TaskRepository
) {
    operator fun invoke(): Flow<List<String>> =
        repository.getTasks().map { it.map { task -> task.name } }
}