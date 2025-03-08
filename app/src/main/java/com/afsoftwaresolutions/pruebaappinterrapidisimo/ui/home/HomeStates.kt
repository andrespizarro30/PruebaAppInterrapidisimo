package com.afsoftwaresolutions.pruebaappinterrapidisimo.ui.home

import com.afsoftwaresolutions.pruebaappinterrapidisimo.data.network.responses.LocalitiesResponse
import com.afsoftwaresolutions.pruebaappinterrapidisimo.domain.model.LocalitiesDataModel
import com.afsoftwaresolutions.pruebaappinterrapidisimo.domain.model.SchemasDataModel
import com.afsoftwaresolutions.pruebaappinterrapidisimo.domain.model.UserDataModel

sealed class HomeStates {

    data object Loading: HomeStates()

    data class ErrorUserData(val error: String): HomeStates()
    data class SuccessUserData(var userDataModel: UserDataModel): HomeStates()

    data class  ErrorLocalitiesData(val error: String): HomeStates()
    data class  SuccessLocalitiesData(val localities: List<LocalitiesDataModel>): HomeStates()

    data class  ErrorSchemasData(val error: String): HomeStates()
    data class  SuccessSchemasData(val schemas: List<SchemasDataModel>): HomeStates()

}