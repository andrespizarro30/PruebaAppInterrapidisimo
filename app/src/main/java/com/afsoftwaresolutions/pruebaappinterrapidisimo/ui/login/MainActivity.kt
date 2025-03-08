package com.afsoftwaresolutions.pruebaappinterrapidisimo.ui.login

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.afsoftwaresolutions.pruebaappinterrapidisimo.BuildConfig
import com.afsoftwaresolutions.pruebaappinterrapidisimo.R
import com.afsoftwaresolutions.pruebaappinterrapidisimo.data.core.network.NetworkMonitor
import com.afsoftwaresolutions.pruebaappinterrapidisimo.databinding.ActivityMainBinding
import com.afsoftwaresolutions.pruebaappinterrapidisimo.domain.model.LoginDataModel
import com.afsoftwaresolutions.pruebaappinterrapidisimo.ui.home.HomeActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val loginViewModel by viewModels<LoginViewModel>()

    lateinit var binding: ActivityMainBinding

    var isConnected = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        initUI()

        verifyAppVersion()

    }

    private fun verifyAppVersion(){

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                loginViewModel.stateAppVersion.collect{
                    when(it){
                        LoginStates.Loading -> loadingState()
                        is LoginStates.ErrorAppVersion -> errorAppVersionState(it)
                        is LoginStates.SuccessAppVersion -> successAppVersionState(it)
                        else -> {}
                    }
                }
            }
        }

        if(isConnected){
            loginViewModel.getAppVersion()
        }else{
            Toast.makeText(this,getString(R.string.network_unavailable),Toast.LENGTH_SHORT).show()
        }

    }

    private fun initUI(){

        loginViewModel.isConnected.observe(this) {
            binding.pb.isVisible = false
            isConnected = it
            val message = if (it) getString(R.string.network_available) else getString(R.string.network_unavailable)
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                loginViewModel.stateLoginProcess.collect{
                    when(it){
                        LoginStates.Loading -> loadingState()
                        is LoginStates.ErrorLogin -> errorLoginState(it)
                        is LoginStates.SuccessLogin -> successLoginState(it)
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

            //THIS MAY CHANGE ACCORDING TO USER BUT IT IS NOT SPECIFIED ON PDF TEST DOCUMENTATION
            //SO THAT'S WHY DATA IS BURNT
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

            if(isConnected){
                loginViewModel.doLogin(headers,loginDataModel)
            }else{
                loginViewModel.doLoginOffLine(loginDataModel)
            }

        }

    }

    private fun loadingState(){
        binding.pb.isVisible = true
    }

    private fun errorAppVersionState(state: LoginStates.ErrorAppVersion){
        binding.pb.isVisible = false
        showDialog(getString(R.string.version_check_title),state.error)
    }

    private fun successAppVersionState(state: LoginStates.SuccessAppVersion){
        binding.pb.isVisible = false

        val localVersion = BuildConfig.VERSION_CODE
        val remoteVersion = state.appVersion

        val message = when {
            localVersion < remoteVersion -> getString(R.string.version_local_lower, localVersion, remoteVersion)
            localVersion > remoteVersion -> getString(R.string.version_local_higher, localVersion, remoteVersion)
            else -> getString(R.string.version_local_equal)
        }

        showDialog(getString(R.string.version_check_title),message)

    }

    private fun errorLoginState(state: LoginStates.ErrorLogin){
        binding.pb.isVisible = false
        showDialog(getString(R.string.login_title),state.error)
    }

    private fun successLoginState(state: LoginStates.SuccessLogin){
        binding.pb.isVisible = false

        val intent = Intent(this, HomeActivity::class.java).apply {
            putExtra("USER", state.userDataModel.user)
            putExtra("PASSWORD", state.userDataModel.password)
        }

        startActivity(intent)

    }

    private fun showDialog(dialogTitle: String,message: String) {
        AlertDialog.Builder(this)
            .setTitle(dialogTitle)
            .setMessage(message)
            .setPositiveButton(getString(R.string.version_check_ok)) { dialog, _ -> dialog.dismiss() }
            .show()
    }

}