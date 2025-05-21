package com.amanzan.cleantemplate.domain.repository

import kotlinx.coroutines.flow.Flow

interface TaskRepository {
    val tasks: Flow<List<String>>

    suspend fun add(name: String)
}


