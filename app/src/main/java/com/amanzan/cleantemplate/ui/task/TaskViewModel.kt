package com.amanzan.cleantemplate.ui.task

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.amanzan.cleantemplate.domain.usecase.TaskUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import com.amanzan.cleantemplate.ui.task.TaskUiState.Error
import com.amanzan.cleantemplate.ui.task.TaskUiState.Loading
import com.amanzan.cleantemplate.ui.task.TaskUiState.Success
import javax.inject.Inject

@HiltViewModel
class TaskViewModel @Inject constructor(
    private val taskUseCase: TaskUseCase
) : ViewModel() {

    val uiState = taskUseCase.tasks
        .map<List<String>, TaskUiState>(::Success)
        .catch { emit(Error(it)) }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), Loading)

    fun addTask(name: String) {
        viewModelScope.launch {
            taskUseCase.addTask(name)
        }
    }
}

sealed interface TaskUiState {
    object Loading : TaskUiState
    data class Error(val throwable: Throwable) : TaskUiState
    data class Success(val data: List<String>) : TaskUiState
}
