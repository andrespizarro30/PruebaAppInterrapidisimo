package com.afsoftwaresolutions.pruebaappinterrapidisimo.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.afsoftwaresolutions.pruebaappinterrapidisimo.data.dao.UserDao
import com.afsoftwaresolutions.pruebaappinterrapidisimo.domain.model.LoginDataModel
import com.afsoftwaresolutions.pruebaappinterrapidisimo.ui.login.LoginStates
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val userDao: UserDao):ViewModel() {

    private var _stateUserData = MutableStateFlow<HomeStates>(HomeStates.Loading)
    var stateUserData: StateFlow<HomeStates> = _stateUserData

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

}