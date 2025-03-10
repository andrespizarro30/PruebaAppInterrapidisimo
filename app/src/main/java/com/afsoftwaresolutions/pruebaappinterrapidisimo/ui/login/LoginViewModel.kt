package com.afsoftwaresolutions.pruebaappinterrapidisimo.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
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

/**
 * ViewModel responsable del proceso de autenticación y gestión de la versión de la aplicación.
 * Permite iniciar sesión tanto en línea como fuera de línea y obtener la versión de la app.
 *
 * @property getAppVersionUC Caso de uso para obtener la versión de la aplicación.
 * @property doLoginUC Caso de uso para realizar el inicio de sesión en línea.
 * @property userDao DAO (Data Access Object) para gestionar la información del usuario en la base de datos local.
 * @property networkMonitor Monitor de red para verificar la conectividad.
 */
@HiltViewModel
class LoginViewModel @Inject constructor(
    private val getAppVersionUC: GetAppVersionUC,
    private val doLoginUC: DoLoginUC,
    private val userDao: UserDao,
    private val networkMonitor: NetworkMonitor
) : ViewModel() {

    /**
     * Estado actual de la versión de la aplicación.
     */
    private var _stateAppVersion = MutableStateFlow<LoginStates>(LoginStates.Loading)

    /**
     * Exposición del estado de la versión de la aplicación como un flujo inmutable.
     */
    var stateAppVersion: StateFlow<LoginStates> = _stateAppVersion

    /**
     * Estado actual del proceso de inicio de sesión.
     */
    private var _stateLoginProcess = MutableStateFlow<LoginStates>(LoginStates.Loading)

    /**
     * Exposición del estado del proceso de inicio de sesión como un flujo inmutable.
     */
    var stateLoginProcess: StateFlow<LoginStates> = _stateLoginProcess

    /**
     * LiveData que expone el estado de la conexión de red.
     */
    val isConnected: LiveData<Boolean> = networkMonitor.isConnected

    private var _isPasswordVisible = MutableLiveData<Boolean>(false)
    val isPasswordVisible: LiveData<Boolean> = _isPasswordVisible

    /**
     * Obtiene la versión de la aplicación y actualiza el estado correspondiente.
     */

    fun getAppVersion(){

        viewModelScope.launch {
            _stateAppVersion.value = LoginStates.Loading
            val result = withContext(Dispatchers.IO){getAppVersionUC()}
            when (result) {
                is LoginStates.SuccessAppVersion -> {
                    _stateAppVersion.value = result
                }
                is LoginStates.ErrorAppVersion -> {
                    _stateAppVersion.value = result
                }
                else -> {}
            }
        }
    }

    /**
     * Realiza el inicio de sesión en línea utilizando las credenciales proporcionadas.
     * Si el inicio de sesión es exitoso, guarda los datos del usuario en la base de datos local.
     *
     * @param headers Encabezados necesarios para la autenticación en el servidor.
     * @param loginDataModel Modelo que contiene las credenciales del usuario.
     */
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

    /**
     * Realiza el inicio de sesión en modo offline verificando las credenciales almacenadas en la base de datos local.
     *
     * @param loginDataModel Modelo que contiene las credenciales del usuario.
     */
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

    fun setNormalState(){
        _stateAppVersion.value = LoginStates.NormalSate
        _stateLoginProcess.value = LoginStates.NormalSate
    }

    fun visibleEditTextPassword(){
        _isPasswordVisible.value = !_isPasswordVisible.value!!
    }

    override fun onCleared() {
        super.onCleared()
    }

}