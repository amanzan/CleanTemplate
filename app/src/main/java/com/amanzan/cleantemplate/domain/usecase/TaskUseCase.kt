package com.amanzan.cleantemplate.domain.usecase

import com.amanzan.cleantemplate.domain.repository.TaskRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TaskUseCase @Inject constructor(
    private val repository: TaskRepository
) {
    val tasks: Flow<List<String>> = repository.tasks

    suspend fun addTask(name: String) {
        repository.add(name)
    }
}

//Use cases do not need a DI module
//Because your TaskUseCase has an @Inject constructor, Hilt knows how to create it when it’s needed.
//So when Hilt needs to create the TaskViewModel, it looks at the constructor:
//"Okay, to create TaskViewModel, I need to provide TaskUseCase."
//"I see TaskUseCase has @Inject constructor, so I can create it by calling that."
//"To create TaskUseCase, I need TaskRepository."
//"I have a binding for TaskRepository (from your module), so I can get that."
//"Perfect! I now have everything to build TaskUseCase and then TaskViewModel."
//So, the chain looks like:
//TaskViewModel <-- needs -- TaskUseCase <-- needs -- TaskRepository <-- provided by -- TaskRepositoryImpl (via your DI module)