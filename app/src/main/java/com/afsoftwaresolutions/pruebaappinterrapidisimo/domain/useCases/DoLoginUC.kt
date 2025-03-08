package com.afsoftwaresolutions.pruebaappinterrapidisimo.domain.useCases

import com.afsoftwaresolutions.pruebaappinterrapidisimo.domain.RepositoryService
import com.afsoftwaresolutions.pruebaappinterrapidisimo.domain.model.LoginDataModel
import javax.inject.Inject

class DoLoginUC @Inject constructor(private val repository: RepositoryService) {

    suspend operator fun invoke(headers: Map<String, String>,loginDataModel: LoginDataModel) = repository.doLogin(headers,loginDataModel)

}