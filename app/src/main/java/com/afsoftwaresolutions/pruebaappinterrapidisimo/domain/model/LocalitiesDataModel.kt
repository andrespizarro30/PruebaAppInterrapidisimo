package com.afsoftwaresolutions.pruebaappinterrapidisimo.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "localities_data")
data class LocalitiesDataModel(
    @PrimaryKey var IdLocalidad: String,
    var Nombre: String? = null,
    var AbreviacionCiudad: String? = null
)