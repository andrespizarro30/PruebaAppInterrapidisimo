package com.afsoftwaresolutions.pruebaappinterrapidisimo.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "schema_data")
data class SchemasDataModel(
    @PrimaryKey var TableName: String
)
