package com.amanzan.cleantemplate.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import com.amanzan.cleantemplate.domain.repository.TaskRepository
import com.amanzan.cleantemplate.data.repository.TaskRepositoryImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    @Singleton
    @Binds
    fun bindsTaskRepository(
        taskRepository: TaskRepositoryImpl
    ): TaskRepository
}
