package com.amanzan.cleantemplate.data.local.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.amanzan.cleantemplate.domain.model.Task
import java.util.UUID

@Entity(tableName = "task")
data class TaskEntity(
    val name: String,
    @PrimaryKey
    val uid: String = UUID.randomUUID().toString()
)

fun TaskEntity.toDomain() = Task(name, uid)
fun Task.toEntity() = TaskEntity(id, name)