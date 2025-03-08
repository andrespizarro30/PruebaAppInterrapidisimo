package com.afsoftwaresolutions.pruebaappinterrapidisimo.data.providers

import android.util.Log
import com.afsoftwaresolutions.pruebaappinterrapidisimo.data.network.ApiService
import com.afsoftwaresolutions.pruebaappinterrapidisimo.data.network.responses.LocalitiesResponse
import com.afsoftwaresolutions.pruebaappinterrapidisimo.data.network.responses.LoginResponse
import com.afsoftwaresolutions.pruebaappinterrapidisimo.data.network.responses.SchemasResponse
import com.afsoftwaresolutions.pruebaappinterrapidisimo.data.testdata.SchemasDataTest
import com.afsoftwaresolutions.pruebaappinterrapidisimo.domain.RepositoryService
import com.afsoftwaresolutions.pruebaappinterrapidisimo.domain.model.LoginDataModel
import com.afsoftwaresolutions.pruebaappinterrapidisimo.ui.home.HomeStates
import com.afsoftwaresolutions.pruebaappinterrapidisimo.ui.login.LoginStates
import retrofit2.HttpException
import javax.inject.Inject

class RepositoryServiceImp@Inject constructor(private val apiService: ApiService):RepositoryService {

    override suspend fun getAppVersion(): Int? {
        val response = runCatching {apiService.getAppVersion()}
            .onSuccess {
                val appVersion : Int = it
                return appVersion
            }
            .onFailure { Log.i("AF SOFT", "Ocurrio un error ${it.message}") }

        return null
    }

    override suspend fun doLogin(headers: Map<String, String>,loginDataModel: LoginDataModel): LoginStates? {
        val response = runCatching {apiService.doLogin(headers,loginDataModel.toResponse())}
            .onSuccess {
                val loginResponse : LoginResponse = it
                return LoginStates.SuccessLogin(loginResponse.toDomain())
            }
            .onFailure {
                val errorMessage = when (it) {
                    is HttpException -> {
                        val statusCode = it.code()
                        val errorBody = it.response()?.errorBody()?.string()
                        "HTTP $statusCode Error: ${errorBody ?: "Unknown error"}"
                    }
                    else -> {
                        "Unexpected error: ${it.message}"
                    }
                }

                return LoginStates.ErrorLogin(errorMessage)
            }

        return null
    }

    override suspend fun getLocalities(): HomeStates? {
        val response = runCatching {apiService.getLocalities()}
            .onSuccess {
                val localities : List<LocalitiesResponse> = it
                return HomeStates.SuccessLocalitiesData(localities.map{ it.toDomain() })
            }
            .onFailure {
                val errorMessage = when (it) {
                    is HttpException -> {
                        val statusCode = it.code()
                        val errorBody = it.response()?.errorBody()?.string()
                        "HTTP $statusCode Error: ${errorBody ?: "Unknown error"}"
                    }
                    else -> {
                        "Unexpected error: ${it.message}"
                    }
                }

                return HomeStates.ErrorLocalitiesData(errorMessage)
            }

        return null
    }

    override suspend fun getSchemas(): HomeStates? {
        //val response = runCatching {apiService.getSchemas()}
        val response = runCatching {SchemasDataTest.getSchemasData()}
            .onSuccess {
                val schemas : List<SchemasResponse> = it
                return HomeStates.SuccessSchemasData(schemas.map{ it.toDomain() })
            }
            .onFailure {
                val errorMessage = when (it) {
                    is HttpException -> {
                        val statusCode = it.code()
                        val errorBody = it.response()?.errorBody()?.string()
                        "HTTP $statusCode Error: ${errorBody ?: "Unknown error"}"
                    }
                    else -> {
                        "Unexpected error: ${it.message}"
                    }
                }

                return HomeStates.ErrorSchemasData(errorMessage)
            }

        return null
    }


}