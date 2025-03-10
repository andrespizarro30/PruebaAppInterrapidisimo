package com.afsoftwaresolutions.pruebaappinterrapidisimo.ui.login

import com.afsoftwaresolutions.pruebaappinterrapidisimo.domain.model.UserDataModel

/**
 * Clase sellada que representa los diferentes estados del proceso de autenticación y obtención de versión de la app.
 */
sealed class LoginStates {

    /**
     * Estado que indica que no hay ningun proceso corriendo o pendiente.
     */
    data object NormalSate : LoginStates()

    /**
     * Estado que indica que el proceso está en carga.
     */
    data object Loading : LoginStates()

    /**
     * Estado que representa un error al obtener la versión de la aplicación.
     *
     * @property error Mensaje descriptivo del error ocurrido.
     */
    data class ErrorAppVersion(val error: String) : LoginStates()

    /**
     * Estado que representa una obtención exitosa de la versión de la aplicación.
     *
     * @property appVersion Número de versión de la aplicación obtenida.
     */
    data class SuccessAppVersion(var appVersion: Int) : LoginStates()

    /**
     * Estado que representa un error en el proceso de inicio de sesión.
     *
     * @property error Mensaje descriptivo del error ocurrido.
     */
    data class ErrorLogin(val error: String) : LoginStates()

    /**
     * Estado que indica un inicio de sesión exitoso.
     *
     * @property userDataModel Datos del usuario autenticado.
     */
    data class SuccessLogin(var userDataModel: UserDataModel) : LoginStates()
}