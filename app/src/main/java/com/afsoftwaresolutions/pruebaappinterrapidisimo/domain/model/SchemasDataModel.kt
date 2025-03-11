package com.afsoftwaresolutions.pruebaappinterrapidisimo.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "schema_data")
data class SchemasDataModel(
    var TableName: String,
    @PrimaryKey val pk: String,
    val numeroCampos: Long,
    val fechaActualizacionSincro: String
)
