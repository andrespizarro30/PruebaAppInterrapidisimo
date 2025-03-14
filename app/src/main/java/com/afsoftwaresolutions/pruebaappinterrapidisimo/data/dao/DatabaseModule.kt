package com.afsoftwaresolutions.pruebaappinterrapidisimo.data.dao

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "inter_database"
        )
        .fallbackToDestructiveMigration()
        .build()
    }

    @Provides
    fun provideUserDao(database: AppDatabase): UserDao {
        return database.userDao()
    }

    @Provides
    fun provideSchemaDao(database: AppDatabase): SchemasDao {
        return database.schemaDao()
    }

    @Provides
    fun provideLocalitiesDao(database: AppDatabase): LocalitiesDao {
        return database.localitiesDao()
    }

}