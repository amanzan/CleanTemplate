/*
 * Copyright (C) 2022 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.amanzan.cleantemplate.data

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test
import com.amanzan.cleantemplate.data.local.database.Task
import com.amanzan.cleantemplate.data.local.database.TaskDao
import com.amanzan.cleantemplate.data.repository.TaskRepositoryImpl

/**
 * Unit tests for [TaskRepositoryImpl].
 */
@OptIn(ExperimentalCoroutinesApi::class) // TODO: Remove when stable
class TaskRepositoryImplTest {

    @Test
    fun tasks_newItemSaved_itemIsReturned() = runTest {
        val repository = TaskRepositoryImpl(FakeTaskDao())

        repository.add("Repository")

        assertEquals(repository.tasks.first().size, 1)
    }

}

private class FakeTaskDao : TaskDao {

    private val data = mutableListOf<Task>()

    override fun getTasks(): Flow<List<Task>> = flow {
        emit(data)
    }

    override suspend fun insertTask(item: Task) {
        data.add(0, item)
    }
}
