package com.afsoftwaresolutions.pruebaappinterrapidisimo.domain

import com.afsoftwaresolutions.pruebaappinterrapidisimo.domain.model.LoginDataModel
import com.afsoftwaresolutions.pruebaappinterrapidisimo.ui.home.HomeStates
import com.afsoftwaresolutions.pruebaappinterrapidisimo.ui.login.LoginStates

interface RepositoryService {

    suspend fun getAppVersion() : Int?

    suspend fun doLogin(headers: Map<String, String>,loginDataModel: LoginDataModel) : LoginStates?

    suspend fun getLocalities() : HomeStates?

    suspend fun getSchemas() : HomeStates?

}