package com.afsoftwaresolutions.pruebaappinterrapidisimo.data.network

import com.afsoftwaresolutions.pruebaappinterrapidisimo.data.network.responses.LocalitiesResponse
import com.afsoftwaresolutions.pruebaappinterrapidisimo.data.network.responses.LoginRequest
import com.afsoftwaresolutions.pruebaappinterrapidisimo.data.network.responses.LoginResponse
import com.afsoftwaresolutions.pruebaappinterrapidisimo.data.network.responses.SchemasResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.HeaderMap
import retrofit2.http.Headers
import retrofit2.http.POST

interface ApiService {

    @GET("/api/version")
    suspend fun getAppVersion() : Int

    @POST("/auth")
    suspend fun doLogin(@HeaderMap headers: Map<String, String>, @Body loginRequest: LoginRequest) : LoginResponse

    @GET("/api/localities")
    suspend fun getLocalities() : List<LocalitiesResponse>

    @GET("/api/schema")
    @Headers("usuario: Controller")
    suspend fun getSchemas() : List<SchemasResponse>

}