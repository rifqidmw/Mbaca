package com.thelazyproject.mbaca.core.data.source.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.thelazyproject.mbaca.core.data.source.local.entity.NovelEntity

@Database(entities = [NovelEntity::class], version = 1, exportSchema = false)
abstract class NovelDatabase : RoomDatabase() {
    abstract fun novelDao(): NovelDao
}

