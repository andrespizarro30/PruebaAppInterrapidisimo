package com.afsoftwaresolutions.pruebaappinterrapidisimo.domain.model

import com.afsoftwaresolutions.pruebaappinterrapidisimo.data.network.responses.LoginRequest

data class LoginDataModel(
    val mac: String,
    val nomAplicacion: String,
    val password: String,
    val path: String,
    val usuario: String,
){
    fun toResponse():LoginRequest{
        return LoginRequest(
            Mac = mac,
            NomAplicacion = nomAplicacion,
            Password = password,
            Path = path,
            Usuario = usuario
        )
    }
}