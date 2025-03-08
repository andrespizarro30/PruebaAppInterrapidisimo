package com.afsoftwaresolutions.pruebaappinterrapidisimo.data.providers

import android.util.Log
import com.afsoftwaresolutions.pruebaappinterrapidisimo.data.network.ApiService
import com.afsoftwaresolutions.pruebaappinterrapidisimo.data.network.responses.LoginResponse
import com.afsoftwaresolutions.pruebaappinterrapidisimo.domain.RepositoryService
import com.afsoftwaresolutions.pruebaappinterrapidisimo.domain.model.LoginDataModel
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


}