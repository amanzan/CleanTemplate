package com.amanzan.cleantemplate.data.remote.api

import com.amanzan.cleantemplate.data.remote.dto.TaskDto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface TaskApi {

    @GET("Tasks")
    suspend fun getTasks(): List<TaskDto>

    @POST("Tasks")
    suspend fun addTask(@Body task: TaskDto): TaskDto
}