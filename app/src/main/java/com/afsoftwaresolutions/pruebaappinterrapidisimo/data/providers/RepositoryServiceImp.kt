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

/**
 * Implementación del servicio de repositorio que maneja la comunicación con la API.
 *
 * @property apiService Servicio de API utilizado para realizar las solicitudes de red.
 */
class RepositoryServiceImp @Inject constructor(private val apiService: ApiService) : RepositoryService {

    /**
     * Obtiene la versión actual de la aplicación desde el servidor.
     *
     * @return La versión de la aplicación si la solicitud es exitosa, `null` en caso de error.
     */
    override suspend fun getAppVersion(): LoginStates? {
        return runCatching { apiService.getAppVersion() }
            .map { LoginStates.SuccessAppVersion(it) }
            .getOrElse {
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
                LoginStates.ErrorAppVersion(errorMessage)
            }
    }

    /**
     * Realiza el inicio de sesión del usuario enviando los datos a la API.
     *
     * @param headers Encabezados HTTP requeridos para la solicitud.
     * @param loginDataModel Datos del usuario para autenticación.
     * @return Un estado de login exitoso o con error, `null` en caso de fallo inesperado.
     */
    override suspend fun doLogin(headers: Map<String, String>, loginDataModel: LoginDataModel): LoginStates? {
        return runCatching { apiService.doLogin(headers, loginDataModel.toResponse()) }
            .map { LoginStates.SuccessLogin(it.toDomain()) }
            .getOrElse {
                val errorMessage = when (it) {
                    is HttpException -> {
                        val statusCode = it.code()
                        val errorBody = it.response()?.errorBody()?.string()
                        "HTTP $statusCode Error: ${errorBody ?: "Error desconocido"}"
                    }
                    else -> {
                        "Error inesperado: ${it.message}"
                    }
                }
                LoginStates.ErrorLogin(errorMessage)
            }
    }

    /**
     * Obtiene la lista de localidades desde la API.
     *
     * @return Un estado con la lista de localidades si la solicitud es exitosa, o un estado de error en caso contrario.
     */
    override suspend fun getLocalities(): HomeStates? {
        return runCatching { apiService.getLocalities() }
            .map { localities -> HomeStates.SuccessLocalitiesData(localities.map { it.toDomain() }) }
            .getOrElse {
                val errorMessage = when (it) {
                    is HttpException -> {
                        val statusCode = it.code()
                        val errorBody = it.response()?.errorBody()?.string()
                        "HTTP $statusCode Error: ${errorBody ?: "Error desconocido"}"
                    }
                    else -> {
                        "Error inesperado: ${it.message}"
                    }
                }
                HomeStates.ErrorLocalitiesData(errorMessage)
            }
    }

    /**
     * Obtiene la lista de esquemas desde la API o desde datos de prueba.
     *
     * @return Un estado con la lista de esquemas si la solicitud es exitosa, o un estado de error en caso contrario.
     */
    override suspend fun getSchemas(): HomeStates? {
        return runCatching { apiService.getSchemas() }
        //return runCatching { SchemasDataTest.getSchemasData() } //En caso de entrevista quisiera explicar este punto y porque no lo elimino
            .map { schemas -> HomeStates.SuccessSchemasData(schemas.map { it.toDomain() }) }
            .getOrElse {
                val errorMessage = when (it) {
                    is HttpException -> {
                        val statusCode = it.code()
                        val errorBody = it.response()?.errorBody()?.string()
                        "HTTP $statusCode Error: ${errorBody ?: "Error desconocido"}"
                    }
                    else -> {
                        "Error inesperado: ${it.message}"
                    }
                }
                HomeStates.ErrorSchemasData(errorMessage)
            }
    }
}