package com.afsoftwaresolutions.pruebaappinterrapidisimo.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.afsoftwaresolutions.pruebaappinterrapidisimo.domain.model.SchemasDataModel
import com.afsoftwaresolutions.pruebaappinterrapidisimo.domain.model.UserDataModel

@Dao
interface SchemasDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSchema(schemasDataModel: SchemasDataModel)

    @Query("SELECT * FROM schema_data")
    suspend fun getSchemas(): List<SchemasDataModel>?


}