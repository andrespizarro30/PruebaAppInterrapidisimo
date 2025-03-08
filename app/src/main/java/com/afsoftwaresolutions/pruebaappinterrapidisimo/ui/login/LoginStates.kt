package com.afsoftwaresolutions.pruebaappinterrapidisimo.ui.login

import com.afsoftwaresolutions.pruebaappinterrapidisimo.domain.model.UserDataModel

sealed class LoginStates {

    data object Loading:LoginStates()

    data class ErrorAppVersion(val error: String):LoginStates()
    data class SuccessAppVersion(var appVersion: Int):LoginStates()

    data class ErrorLogin(val error: String):LoginStates()
    data class SuccessLogin(var userDataModel: UserDataModel):LoginStates()

}