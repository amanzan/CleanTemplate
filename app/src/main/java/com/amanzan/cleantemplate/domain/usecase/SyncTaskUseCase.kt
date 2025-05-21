package com.amanzan.cleantemplate.domain.usecase

import com.amanzan.cleantemplate.domain.repository.TaskRepository
import javax.inject.Inject

class SyncTasksUseCase @Inject constructor(
    private val repository: TaskRepository
) {
    suspend operator fun invoke() {
        repository.syncFromRemote()
    }
}