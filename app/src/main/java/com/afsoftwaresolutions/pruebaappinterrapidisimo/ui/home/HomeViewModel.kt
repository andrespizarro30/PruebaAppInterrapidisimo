package com.afsoftwaresolutions.pruebaappinterrapidisimo.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.afsoftwaresolutions.pruebaappinterrapidisimo.data.core.network.NetworkMonitor
import com.afsoftwaresolutions.pruebaappinterrapidisimo.data.dao.LocalitiesDao
import com.afsoftwaresolutions.pruebaappinterrapidisimo.data.dao.SchemasDao
import com.afsoftwaresolutions.pruebaappinterrapidisimo.data.dao.UserDao
import com.afsoftwaresolutions.pruebaappinterrapidisimo.domain.useCases.GetLocalitiesUC
import com.afsoftwaresolutions.pruebaappinterrapidisimo.domain.useCases.GetSchemasUC
import com.afsoftwaresolutions.pruebaappinterrapidisimo.ui.login.LoginStates
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * ViewModel responsable de gestionar las operaciones de la pantalla principal,
 * incluyendo la autenticación de usuarios, la obtención de localidades y la obtención de las esquemas de tablas.
 *
 * @property userDao DAO (Data Access Object) para operaciones relacionadas con usuarios en la base de datos.
 * @property getLocalities Caso de uso para obtener datos de localidades.
 * @property getSchemasUC Caso de uso para obtener datos de esquemas.
 * @property schemasDao DAO para operaciones de esquemas en la base de datos.
 */

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val userDao: UserDao,
    private val getLocalities: GetLocalitiesUC,
    private val getSchemasUC: GetSchemasUC,
    private val schemasDao: SchemasDao,
    private val localitiesDao: LocalitiesDao,
    private val networkMonitor: NetworkMonitor
):ViewModel() {

    /**
     * Estado actual de la autenticación del usuario.
     */
    private var _stateUserData = MutableStateFlow<HomeStates>(HomeStates.Loading)

    /**
     * Exposición del estado de autenticación del usuario como un flujo inmutable.
     */
    var stateUserData: StateFlow<HomeStates> = _stateUserData

    /**
     * Estado actual de la obtención de datos de localidades.
     */
    private var _stateLocalitiesData = MutableStateFlow<HomeStates>(HomeStates.Loading)

    /**
     * Exposición del estado de los datos de localidades como un flujo inmutable.
     */
    var stateLocalitiesData: StateFlow<HomeStates> = _stateLocalitiesData

    /**
     * Estado actual de la obtención de datos de esquemas.
     */
    private var _stateSchemasData = MutableStateFlow<HomeStates>(HomeStates.Loading)

    /**
     * Exposición del estado de los datos de esquemas como un flujo inmutable.
     */
    var stateSchemasData: StateFlow<HomeStates> = _stateSchemasData

    /**
     * LiveData que expone el estado de la conexión de red.
     */
    val isConnected: LiveData<Boolean> = networkMonitor.isConnected

    /**
     * Intenta iniciar sesión en modo offline verificando las credenciales almacenadas en la base de datos local.
     *
     * @param user Nombre de usuario para la autenticación.
     * @param password Contraseña asociada al usuario.
     */

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

    /**
     * Obtiene los datos de las localidades a través del caso de uso y actualiza el estado correspondiente.
     */
    fun getLocalitiesData(){
        viewModelScope.launch {

            _stateLocalitiesData.value = HomeStates.Loading

            val result: HomeStates? = withContext(Dispatchers.IO){getLocalities()}

            when (result) {
                is HomeStates.SuccessLocalitiesData -> {
                    result.localities.forEach{localitiesDao.insertLocality(it)}
                    _stateLocalitiesData.value = result
                }
                is HomeStates.ErrorLocalitiesData -> {
                    _stateLocalitiesData.value = result
                }
                else -> {}
            }

        }
    }

    fun getLocalitiesDataOffline(){
        viewModelScope.launch {

            _stateSchemasData.value = HomeStates.Loading

            val result = withContext(Dispatchers.IO){localitiesDao.getLocalities()}

            if(result!=null){
                _stateLocalitiesData.value = HomeStates.SuccessLocalitiesData(result)
            }else{
                _stateLocalitiesData.value = HomeStates.ErrorLocalitiesData("Sin red disponible, no hay datos guardados")
            }

        }
    }

    /**
     * Obtiene los datos de los esquemas a través del caso de uso, los inserta en la base de datos local
     * y actualiza el estado correspondiente.
     */
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

    fun getSchemasDataOffline(){
        viewModelScope.launch {

            _stateSchemasData.value = HomeStates.Loading

            val result = withContext(Dispatchers.IO){schemasDao.getSchemas()}

            if(result!=null){
                _stateSchemasData.value = HomeStates.SuccessSchemasData(result)
            }else{
                _stateSchemasData.value = HomeStates.ErrorSchemasData("Sin red disponible, no hay datos guardados")
            }

        }
    }

}