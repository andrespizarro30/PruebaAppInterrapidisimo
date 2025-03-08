package com.afsoftwaresolutions.pruebaappinterrapidisimo.data.dao

import androidx.room.Database
import androidx.room.RoomDatabase
import com.afsoftwaresolutions.pruebaappinterrapidisimo.domain.model.UserDataModel

@Database(entities = [UserDataModel::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
}