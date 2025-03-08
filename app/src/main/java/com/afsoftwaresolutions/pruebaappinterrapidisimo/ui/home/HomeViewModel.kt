package com.afsoftwaresolutions.pruebaappinterrapidisimo.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.afsoftwaresolutions.pruebaappinterrapidisimo.data.dao.SchemasDao
import com.afsoftwaresolutions.pruebaappinterrapidisimo.data.dao.UserDao
import com.afsoftwaresolutions.pruebaappinterrapidisimo.domain.useCases.GetLocalitiesUC
import com.afsoftwaresolutions.pruebaappinterrapidisimo.domain.useCases.GetSchemasUC
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val userDao: UserDao,
    private val getLocalities: GetLocalitiesUC,
    private val getSchemasUC: GetSchemasUC,
    private val schemasDao: SchemasDao
):ViewModel() {

    private var _stateUserData = MutableStateFlow<HomeStates>(HomeStates.Loading)
    var stateUserData: StateFlow<HomeStates> = _stateUserData

    private var _stateLocalitiesData = MutableStateFlow<HomeStates>(HomeStates.Loading)
    var stateLocalitiesData: StateFlow<HomeStates> = _stateLocalitiesData

    private var _stateSchemasData = MutableStateFlow<HomeStates>(HomeStates.Loading)
    var stateSchemasData: StateFlow<HomeStates> = _stateSchemasData

    fun doLoginOffLine(user:String,password:String){

        viewModelScope.launch {

            _stateUserData.value = HomeStates.Loading

            val result = withContext(Dispatchers.IO){userDao.getUserByUserAndPassword(user,password)}

            if(result != null){
                _stateUserData.value = HomeStates.SuccessUserData(result)
            }else{
                _stateUserData.value = HomeStates.ErrorUserData("Error")
            }

        }

    }

    fun getLocalitiesData(){
        viewModelScope.launch {

            _stateLocalitiesData.value = HomeStates.Loading

            val result: HomeStates? = withContext(Dispatchers.IO){getLocalities()}

            when (result) {
                is HomeStates.SuccessLocalitiesData -> {
                    _stateLocalitiesData.value = result
                }
                is HomeStates.ErrorLocalitiesData -> {
                    _stateLocalitiesData.value = result
                }
                else -> {}
            }

        }
    }

    fun getSchemasData(){
        viewModelScope.launch {

            _stateSchemasData.value = HomeStates.Loading

            val result: HomeStates? = withContext(Dispatchers.IO){getSchemasUC()}

            when (result) {
                is HomeStates.SuccessSchemasData -> {
                    result.schemas.forEach{ schemasDao.insertSchema(it) }
                    _stateSchemasData.value = result
                }
                is HomeStates.ErrorSchemasData -> {
                    _stateSchemasData.value = result
                }
                else -> {}
            }

        }
    }

}