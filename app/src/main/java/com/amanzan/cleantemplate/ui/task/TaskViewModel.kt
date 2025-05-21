package com.amanzan.cleantemplate.ui.task

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.amanzan.cleantemplate.domain.usecase.AddTaskUseCase
import com.amanzan.cleantemplate.domain.usecase.GetTasksUseCase
import com.amanzan.cleantemplate.domain.usecase.SyncTasksUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TaskViewModel @Inject constructor(
    private val getTasksUseCase: GetTasksUseCase,
    private val addTaskUseCase: AddTaskUseCase,
    private val syncTasksUseCase: SyncTasksUseCase
) : ViewModel() {

    private val _isShowingCachedData = MutableStateFlow(true)  // Start with cached data assumption
    private val isShowingCachedData = _isShowingCachedData.asStateFlow()

    val uiState = combine(
        getTasksUseCase(),
        isShowingCachedData
    ) { tasks, isCached ->
        TaskUiState.Success(tasks, isCached) as TaskUiState
    }
    .catch {
        Log.e("TaskViewModel", "Error loading tasks", it)
        emit(TaskUiState.Error(it))
    }
    .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), TaskUiState.Loading)

    init {
        syncOnStartup()
    }

    fun addTask(name: String) {
        viewModelScope.launch {
            try {
                addTaskUseCase(name)
                syncWithRemote()
            } catch (e: Exception) {
                // optionally log or emit an error side effect
            }
        }
    }

    private fun syncOnStartup() {
        syncWithRemote()
    }

    private fun syncWithRemote() {
        viewModelScope.launch {
            try {
                syncTasksUseCase()
                _isShowingCachedData.value = false  // Successfully synced with remote
            } catch (e: Exception) {
                _isShowingCachedData.value = true  // Failed to sync, showing cached
                Log.e("TaskViewModel", "Failed to sync with remote", e)
            }
        }
    }
}

sealed interface TaskUiState {
    object Loading : TaskUiState
    data class Error(val throwable: Throwable) : TaskUiState
    data class Success(
        val data: List<String>,
        val isShowingCachedData: Boolean
    ) : TaskUiState
}
