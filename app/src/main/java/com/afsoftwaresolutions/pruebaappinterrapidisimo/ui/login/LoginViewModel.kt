package com.afsoftwaresolutions.pruebaappinterrapidisimo.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.afsoftwaresolutions.pruebaappinterrapidisimo.data.core.network.NetworkMonitor
import com.afsoftwaresolutions.pruebaappinterrapidisimo.data.dao.UserDao
import com.afsoftwaresolutions.pruebaappinterrapidisimo.domain.model.LoginDataModel
import com.afsoftwaresolutions.pruebaappinterrapidisimo.domain.useCases.DoLoginUC
import com.afsoftwaresolutions.pruebaappinterrapidisimo.domain.useCases.GetAppVersionUC
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val getAppVersionUC: GetAppVersionUC,
    private val doLoginUC: DoLoginUC,
    private val userDao: UserDao,
    private val networkMonitor: NetworkMonitor
) : ViewModel() {

    private var _stateAppVersion = MutableStateFlow<LoginStates>(LoginStates.Loading)
    var stateAppVersion: StateFlow<LoginStates> = _stateAppVersion

    private var _stateLoginProcess = MutableStateFlow<LoginStates>(LoginStates.Loading)
    var stateLoginProcess: StateFlow<LoginStates> = _stateLoginProcess

    val isConnected: LiveData<Boolean> = networkMonitor.isConnected

    fun getAppVersion(){
        viewModelScope.launch {
            _stateAppVersion.value = LoginStates.Loading
            val result = withContext(Dispatchers.IO){getAppVersionUC()}
            if(result!=null){
                _stateAppVersion.value = LoginStates.SuccessAppVersion(result)
            }else{
                _stateAppVersion.value = LoginStates.ErrorAppVersion("Ha ocurrido un error, intentelo mas tarde")
            }
        }
    }

    fun doLogin(headers: Map<String, String>, loginDataModel: LoginDataModel){
        viewModelScope.launch {

            _stateLoginProcess.value = LoginStates.Loading

            val result : LoginStates? = withContext(Dispatchers.IO){doLoginUC(headers,loginDataModel)}

            when (result) {
                is LoginStates.SuccessLogin -> {
                    val userData = result.userDataModel
                    userData.user = loginDataModel.usuario
                    userData.password = loginDataModel.password

                    userDao.insertUser(result.userDataModel)

                    _stateLoginProcess.value = result
                }
                is LoginStates.ErrorLogin -> {
                    _stateLoginProcess.value = result
                }
                else -> {}
            }
        }
    }

    fun doLoginOffLine(loginDataModel: LoginDataModel){
        viewModelScope.launch {

            _stateLoginProcess.value = LoginStates.Loading

            val result = withContext(Dispatchers.IO){userDao.getUserByUserAndPassword(loginDataModel.usuario,loginDataModel.password)}

            if(result != null){
                _stateLoginProcess.value = LoginStates.SuccessLogin(result)
            }else{
                _stateLoginProcess.value = LoginStates.ErrorLogin("Sin red disponible, no hay datos guardados")
            }

        }
    }

    override fun onCleared() {
        super.onCleared()
    }

}