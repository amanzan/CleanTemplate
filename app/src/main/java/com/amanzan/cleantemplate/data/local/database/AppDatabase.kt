package com.amanzan.cleantemplate.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.amanzan.cleantemplate.data.local.database.dao.TaskDao
import com.amanzan.cleantemplate.data.local.database.entity.TaskEntity

@Database(entities = [TaskEntity::class], version = 3)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun taskDao(): TaskDao
}
