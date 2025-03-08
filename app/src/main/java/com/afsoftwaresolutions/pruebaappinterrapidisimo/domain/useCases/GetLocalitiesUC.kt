package com.afsoftwaresolutions.pruebaappinterrapidisimo.domain.useCases

import com.afsoftwaresolutions.pruebaappinterrapidisimo.domain.RepositoryService
import javax.inject.Inject

class GetLocalitiesUC @Inject constructor(private val repository: RepositoryService) {

    suspend operator fun invoke() = repository.getLocalities()

}