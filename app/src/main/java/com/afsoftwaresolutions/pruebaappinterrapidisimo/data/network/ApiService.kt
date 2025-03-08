package com.afsoftwaresolutions.pruebaappinterrapidisimo.data.network

import com.afsoftwaresolutions.pruebaappinterrapidisimo.data.network.responses.LoginRequest
import com.afsoftwaresolutions.pruebaappinterrapidisimo.data.network.responses.LoginResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.HeaderMap
import retrofit2.http.POST

interface ApiService {

    @GET("/api/version")
    suspend fun getAppVersion() : Int

    @POST("/auth")
    suspend fun doLogin(@HeaderMap headers: Map<String, String>, @Body loginRequest: LoginRequest) : LoginResponse

}