package com.afsoftwaresolutions.pruebaappinterrapidisimo.ui.home

import com.afsoftwaresolutions.pruebaappinterrapidisimo.data.network.responses.LocalitiesResponse
import com.afsoftwaresolutions.pruebaappinterrapidisimo.domain.model.LocalitiesDataModel
import com.afsoftwaresolutions.pruebaappinterrapidisimo.domain.model.SchemasDataModel
import com.afsoftwaresolutions.pruebaappinterrapidisimo.domain.model.UserDataModel

/**
 * Clase sellada que representa los diferentes estados posibles en la pantalla principal (Home).
 */
sealed class HomeStates {

    /**
     * Estado que indica que el proceso está en carga.
     */
    data object Loading : HomeStates()

    /**
     * Estado que representa un error al obtener los datos del usuario.
     *
     * @property error Mensaje descriptivo del error ocurrido.
     */
    data class ErrorUserData(val error: String) : HomeStates()

    /**
     * Estado que indica que los datos del usuario se obtuvieron correctamente.
     *
     * @property userDataModel Datos del usuario autenticado.
     */
    data class SuccessUserData(var userDataModel: UserDataModel) : HomeStates()

    /**
     * Estado que representa un error al obtener las localidades.
     *
     * @property error Mensaje descriptivo del error ocurrido.
     */
    data class ErrorLocalitiesData(val error: String) : HomeStates()

    /**
     * Estado que indica que las localidades se obtuvieron correctamente.
     *
     * @property localities Lista de localidades obtenidas.
     */
    data class SuccessLocalitiesData(val localities: List<LocalitiesDataModel>) : HomeStates()

    /**
     * Estado que representa un error al obtener los esquemas.
     *
     * @property error Mensaje descriptivo del error ocurrido.
     */
    data class ErrorSchemasData(val error: String) : HomeStates()

    /**
     * Estado que indica que los esquemas se obtuvieron correctamente.
     *
     * @property schemas Lista de esquemas obtenidos.
     */
    data class SuccessSchemasData(val schemas: List<SchemasDataModel>) : HomeStates()
}