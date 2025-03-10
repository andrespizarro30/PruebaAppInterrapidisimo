package com.afsoftwaresolutions.pruebaappinterrapidisimo.ui.login

import android.content.Intent
import android.os.Build.VERSION
import android.os.Build.VERSION_CODES
import android.os.Bundle
import android.text.InputType
import android.view.MotionEvent
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.afsoftwaresolutions.pruebaappinterrapidisimo.BuildConfig
import com.afsoftwaresolutions.pruebaappinterrapidisimo.R
import com.afsoftwaresolutions.pruebaappinterrapidisimo.databinding.ActivityMainBinding
import com.afsoftwaresolutions.pruebaappinterrapidisimo.domain.model.LoginDataModel
import com.afsoftwaresolutions.pruebaappinterrapidisimo.ui.helpers.DialogHelper
import com.afsoftwaresolutions.pruebaappinterrapidisimo.ui.home.HomeActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

/**
 * Actividad principal de la aplicación.
 *
 * Se encarga de manejar el proceso de autenticación del usuario, verificar la versión de la aplicación
 * y gestionar la conectividad de red.
 */
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    /** ViewModel para manejar el estado de inicio de sesión y la versión de la aplicación */
    private val loginViewModel by viewModels<LoginViewModel>()

    /** Enlace a la vista mediante ViewBinding */
    lateinit var binding: ActivityMainBinding

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
        binding = ActivityMainBinding.inflate(layoutInflater)

        // Establece la vista de la actividad
        setContentView(binding.root)

        // Inicializa la interfaz de usuario
        initUI()

        // Verifica la versión de la aplicación
        verifyAppVersion()
    }

    /**
     * Verifica la versión de la aplicación a través del ViewModel.
     */
    private fun verifyAppVersion() {

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                loginViewModel.stateAppVersion.collect { state ->
                    when (state) {
                        LoginStates.Loading -> loadingState()
                        is LoginStates.ErrorAppVersion -> errorAppVersionState(state)
                        is LoginStates.SuccessAppVersion -> successAppVersionState(state)
                        else -> {}
                    }
                }
            }
        }
        if (isConnected) {
            loginViewModel.getAppVersion()
        } else {
            Toast.makeText(this, getString(R.string.network_unavailable), Toast.LENGTH_SHORT).show()
        }
    }

    /**
     * Inicializa la interfaz de usuario, incluyendo la observación del estado de la red
     * y el proceso de inicio de sesión.
     */
    private fun initUI() {

        loginViewModel.isConnected.observe(this) { connected ->
            binding.pb.isVisible = false
            isConnected = connected
            val message = if (connected) getString(R.string.network_available) else getString(R.string.network_unavailable)
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                loginViewModel.stateLoginProcess.collect { state ->
                    when (state) {
                        LoginStates.Loading -> loadingState()
                        is LoginStates.ErrorLogin -> errorLoginState(state)
                        is LoginStates.SuccessLogin -> successLoginState(state)
                        else -> {}
                    }
                }
            }
        }

        binding.btnLogin.setOnClickListener {

            val loginDataModel = LoginDataModel(
                mac = "",
                nomAplicacion = "Controller APP",
                password = binding.etPassword.text.toString(),
                path = "",
                usuario = binding.etUsername.text.toString()
            )

            // Esto puede cambiar según el usuario, pero en la documentación de la prueba no está especificado,
            // por lo que los datos están quemados (hardcoded).
            val headers = mapOf(
                "Usuario" to "pam.meredy21",
                "Identificacion" to "987204545",
                "Accept" to "text/json",
                "IdUsuario" to "pam.meredy21",
                "IdCentroServicio" to "1295",
                "NombreCentroServicio" to "PTO/BOGOTA/CUND/COL/OF PRINCIPAL - CRA30#7-45",
                "IdAplicativoOrigen" to "9",
                "Content-Type" to "application/json"
            )

            if (isConnected) {
                loginViewModel.doLogin(headers, loginDataModel)
            } else {
                loginViewModel.doLoginOffLine(loginDataModel)
            }

        }

        loginViewModel.isPasswordVisible.observe(this) { isVisible ->
            if (!isVisible) {
                // Hide password
                binding.etPassword.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
                binding.etPassword.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_password, 0, R.drawable.ic_eye, 0)
            } else {
                // Show password
                binding.etPassword.inputType = InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
                binding.etPassword.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_password, 0, R.drawable.ic_eye_off, 0)
            }
            binding.etPassword.setSelection(binding.etPassword.text.length)
        }

        binding.etPassword.setOnClickListener{
            loginViewModel.visibleEditTextPassword()
        }

    }

    /**
     * Muestra el estado de carga mientras se realizan procesos.
     */
    private fun loadingState() {
        binding.pb.isVisible = true
    }

    /**
     * Maneja el estado de error al verificar la versión de la aplicación.
     *
     * @param state Estado de error con el mensaje correspondiente.
     */
    private fun errorAppVersionState(state: LoginStates.ErrorAppVersion) {
        binding.pb.isVisible = false
        DialogHelper.showDialog(this, getString(R.string.version_check_title), state.error)
    }

    /**
     * Maneja el estado de éxito al verificar la versión de la aplicación.
     *
     * @param state Estado de éxito con la versión obtenida.
     */
    private fun successAppVersionState(state: LoginStates.SuccessAppVersion) {
        binding.pb.isVisible = false

        val localVersion = BuildConfig.VERSION_CODE
        val remoteVersion = state.appVersion

        val message = when {
            localVersion < remoteVersion -> getString(R.string.version_local_lower, localVersion, remoteVersion)
            localVersion > remoteVersion -> getString(R.string.version_local_higher, localVersion, remoteVersion)
            else -> getString(R.string.version_local_equal)
        }

        DialogHelper.showDialog(this, getString(R.string.version_check_title), message)
    }

    /**
     * Maneja el estado de error al iniciar sesión.
     *
     * @param state Estado de error con el mensaje correspondiente.
     */
    private fun errorLoginState(state: LoginStates.ErrorLogin) {
        binding.pb.isVisible = false
        DialogHelper.showDialog(this, getString(R.string.login_title), state.error)
    }

    /**
     * Maneja el estado de éxito al iniciar sesión y redirige a la pantalla de inicio (HomeActivity).
     *
     * @param state Estado de éxito con los datos del usuario autenticado.
     */
    private fun successLoginState(state: LoginStates.SuccessLogin) {

        binding.pb.isVisible = false

        loginViewModel.setNormalState()

        val intent = Intent(this, HomeActivity::class.java).apply {
            putExtra("USER", state.userDataModel.user)
            putExtra("PASSWORD", state.userDataModel.password)
        }

        startActivity(intent)
    }


    override fun onResume() {
        super.onResume()
    }

}