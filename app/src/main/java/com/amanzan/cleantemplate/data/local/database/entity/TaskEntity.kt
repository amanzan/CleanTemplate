package com.amanzan.cleantemplate.data.local.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.amanzan.cleantemplate.domain.model.Task
import java.util.UUID

enum class SyncStatus {
    PENDING,
    SYNCED
}

@Entity(tableName = "task")
data class TaskEntity(
    val name: String,
    @PrimaryKey
    val uid: String = UUID.randomUUID().toString(),
    val syncStatus: SyncStatus = SyncStatus.PENDING
)

fun TaskEntity.toDomain() = Task(uid, name)
fun Task.toEntity() = TaskEntity(name = name, uid = id)