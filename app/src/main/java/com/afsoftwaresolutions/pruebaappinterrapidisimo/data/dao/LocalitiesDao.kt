package com.afsoftwaresolutions.pruebaappinterrapidisimo.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.afsoftwaresolutions.pruebaappinterrapidisimo.domain.model.LocalitiesDataModel

@Dao
interface LocalitiesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLocality(localitiesDataModel: LocalitiesDataModel)

    @Query("SELECT * FROM localities_data")
    suspend fun getLocalities(): List<LocalitiesDataModel>?

}