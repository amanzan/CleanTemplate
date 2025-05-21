package com.amanzan.cleantemplate.data.local.database

import androidx.room.TypeConverter
import com.amanzan.cleantemplate.data.local.database.entity.SyncStatus

class Converters {
    @TypeConverter
    fun fromSyncStatus(value: SyncStatus): String {
        return value.name
    }

    @TypeConverter
    fun toSyncStatus(value: String): SyncStatus {
        return SyncStatus.valueOf(value)
    }
} 