package com.afsoftwaresolutions.pruebaappinterrapidisimo.ui.home

import com.afsoftwaresolutions.pruebaappinterrapidisimo.domain.model.UserDataModel
import com.afsoftwaresolutions.pruebaappinterrapidisimo.ui.login.LoginStates

sealed class HomeStates {

    data object Loading: HomeStates()

    data class ErrorUserData(val error: String): HomeStates()
    data class SuccessUserData(var userDataModel: UserDataModel): HomeStates()

}