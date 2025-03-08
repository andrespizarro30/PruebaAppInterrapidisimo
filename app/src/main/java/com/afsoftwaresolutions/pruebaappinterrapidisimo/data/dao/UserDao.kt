package com.afsoftwaresolutions.pruebaappinterrapidisimo.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.afsoftwaresolutions.pruebaappinterrapidisimo.domain.model.UserDataModel

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: UserDataModel)

    @Query("SELECT * FROM user_data WHERE user = :user AND password = :password LIMIT 1")
    suspend fun getUserByUserAndPassword(user: String, password: String): UserDataModel?

    @Query("DELETE FROM user_data")
    suspend fun deleteAllUsers()

}