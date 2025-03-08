package com.afsoftwaresolutions.pruebaappinterrapidisimo.ui.home

import android.os.Bundle
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

@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {

    lateinit var binding: ActivityHomeBinding
    private lateinit var navController: NavController

    private val homeViewModel by viewModels<HomeViewModel>()

    private lateinit var user: String
    private lateinit var password: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityHomeBinding.inflate(layoutInflater)

        setContentView(binding.root)

        user = intent.getStringExtra("USER") ?: "No User"
        password = intent.getStringExtra("PASSWORD") ?: "No Password"

        initUI()

    }

    private fun initUI() {

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                homeViewModel.stateUserData.collect{
                    when(it){
                        HomeStates.Loading -> loadingState()
                        is HomeStates.ErrorUserData -> errorDataUserState(it)
                        is HomeStates.SuccessUserData -> successDataUserState(it)
                    }
                }
            }
        }

        homeViewModel.doLoginOffLine(user,password)

        initNavigation()
    }

    private fun loadingState(){

    }

    private fun errorDataUserState(state: HomeStates.ErrorUserData){

    }

    private fun successDataUserState(state: HomeStates.SuccessUserData){

        binding.etUser.text = state.userDataModel.Usuario
        binding.etIdentification.text = state.userDataModel.Identificacion
        binding.etName.text = state.userDataModel.Nombre

    }

    private fun initNavigation() {
        val navHost = supportFragmentManager.findFragmentById(binding.fragmentContainerView.id) as NavHostFragment
        navController = navHost.navController
        binding.bottomNavView.setupWithNavController(navController)
    }



}