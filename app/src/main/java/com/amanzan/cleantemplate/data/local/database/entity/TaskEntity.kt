package com.amanzan.cleantemplate.data.local.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "task")
data class TaskEntity(
    val name: String,
    @PrimaryKey(autoGenerate = true)
    val uid: Int = 0
)
