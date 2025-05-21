package com.amanzan.cleantemplate.data.remote.dto

import com.amanzan.cleantemplate.data.local.database.entity.TaskEntity
import com.amanzan.cleantemplate.domain.model.Task

data class TaskDto(
    val id: String,
    val name: String
)

fun TaskDto.toDomain() = Task(id, name)
fun Task.toDto() = TaskDto(id, name)
fun TaskDto.toEntity() = TaskEntity(id, name)