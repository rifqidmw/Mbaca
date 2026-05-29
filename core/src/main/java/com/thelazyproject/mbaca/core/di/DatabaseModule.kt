package com.thelazyproject.mbaca.core.di

import android.content.Context
import androidx.room.Room
import androidx.sqlite.db.SupportSQLiteDatabase
import com.thelazyproject.mbaca.core.data.source.local.room.NovelDao
import com.thelazyproject.mbaca.core.data.source.local.room.NovelDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import net.sqlcipher.database.SQLiteDatabase
import net.sqlcipher.database.SupportFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): NovelDatabase {
        val passphrase: ByteArray = SQLiteDatabase.getBytes("MbacaSecureDB2024!@#".toCharArray())
        val factory = SupportFactory(passphrase)

        return Room.databaseBuilder(
            context,
            NovelDatabase::class.java,
            "Novel.db"
        )
        .openHelperFactory(factory)
        .fallbackToDestructiveMigration()
        .build()
    }

    @Provides
    fun provideNovelDao(database: NovelDatabase): NovelDao = database.novelDao()
}

