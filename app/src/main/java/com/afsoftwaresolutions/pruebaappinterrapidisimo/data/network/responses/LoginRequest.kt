package com.afsoftwaresolutions.pruebaappinterrapidisimo.data.network.responses

data class LoginRequest(
    val Mac: String,
    val NomAplicacion: String,
    val Password: String,
    val Path: String,
    val Usuario: String,
)
