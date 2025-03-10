package com.afsoftwaresolutions.pruebaappinterrapidisimo.ui.home

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.afsoftwaresolutions.pruebaappinterrapidisimo.BuildConfig
import com.afsoftwaresolutions.pruebaappinterrapidisimo.R
import com.afsoftwaresolutions.pruebaappinterrapidisimo.databinding.ActivityHomeBinding
import com.afsoftwaresolutions.pruebaappinterrapidisimo.databinding.ActivityMainBinding
import com.afsoftwaresolutions.pruebaappinterrapidisimo.ui.login.LoginStates
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

/**
 * Actividad principal de la pantalla de inicio (Home).
 *
 * Se encarga de inicializar la interfaz de usuario, gestionar la navegación y obtener datos del usuario,
 * localidades y esquemas almacenados localmente.
 */
@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {

    /** Enlace a la vista mediante ViewBinding */
    lateinit var binding: ActivityHomeBinding

    /** Controlador de navegación para manejar la navegación entre fragmentos */
    private lateinit var navController: NavController

    /** ViewModel de la pantalla de inicio, inyectado con Hilt */
    private val homeViewModel by viewModels<HomeViewModel>()

    /** Nombre de usuario obtenido desde la intención */
    private lateinit var user: String

    /** Contraseña del usuario obtenida desde la intención */
    private lateinit var password: String

    /** Indica si el dispositivo está conectado a Internet */
    var isConnected = true

    /**
     * Método que se ejecuta al crear la actividad.
     *
     * @param savedInstanceState Estado guardado de la actividad en caso de recreación.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inicializa ViewBinding
        binding = ActivityHomeBinding.inflate(layoutInflater)

        // Establece la vista de la actividad
        setContentView(binding.root)

        // Obtiene el usuario y la contraseña desde los extras de la intención
        user = intent.getStringExtra("USER") ?: "No User"
        password = intent.getStringExtra("PASSWORD") ?: "No Password"

        // Inicializa la interfaz de usuario
        initUI()
    }

    /**
     * Inicializa la interfaz de usuario y configura la observación de los estados del ViewModel.
     */
    private fun initUI() {

        homeViewModel.isConnected.observe(this) { connected ->
            isConnected = connected
            val message = if (connected) getString(R.string.network_available) else getString(R.string.network_unavailable)
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        }

        isConnected = homeViewModel.isConnected.value!!

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                homeViewModel.stateUserData.collect { state ->
                    when (state) {
                        HomeStates.Loading -> loadingState()
                        is HomeStates.ErrorUserData -> errorDataUserState(state)
                        is HomeStates.SuccessUserData -> successDataUserState(state)
                        else -> {}
                    }
                }
            }
        }

        // Ejecuta proceso para obtener datos almacenados localmente
        homeViewModel.doLoginOffLine(user, password)

        if(isConnected){
            // Ejecuta procesos para obtener datos de localities y schemas desde el api
            homeViewModel.getLocalitiesData()
            homeViewModel.getSchemasData()
        }else{
            // Ejecuta procesos para obtener datos de localities y schemas desde la bases de datos local (si tiene datos almacenados)
            homeViewModel.getLocalitiesDataOffline()
            homeViewModel.getSchemasDataOffline()
        }


        // Inicializa la navegación
        initNavigation()
    }

    /**
     * Maneja el estado de carga de los datos del usuario.
     */
    private fun loadingState() {
        // Implementar acciones en caso de carga
    }

    /**
     * Maneja el estado de error al obtener los datos del usuario.
     *
     * @param state Estado de error con el mensaje correspondiente.
     */
    private fun errorDataUserState(state: HomeStates.ErrorUserData) {
        // Implementar acciones en caso de error
    }

    /**
     * Maneja el estado de éxito al obtener los datos del usuario.
     *
     * @param state Estado de éxito con la información del usuario.
     */
    private fun successDataUserState(state: HomeStates.SuccessUserData) {
        binding.etUser.text = state.userDataModel.Usuario
        binding.etIdentification.text = state.userDataModel.Identificacion
        binding.etName.text = state.userDataModel.Nombre
    }

    /**
     * Inicializa la navegación dentro de la actividad usando NavController y BottomNavigationView.
     */
    private fun initNavigation() {
        val navHost = supportFragmentManager.findFragmentById(binding.fragmentContainerView.id) as NavHostFragment
        navController = navHost.navController
        binding.bottomNavView.setupWithNavController(navController)
    }
}