package com.afsoftwaresolutions.pruebaappinterrapidisimo.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_data")
data class UserDataModel(
    val Usuario: String?,
    @PrimaryKey val Identificacion: String,
    val Nombre: String?,
    var user: String?,
    var password: String?
)