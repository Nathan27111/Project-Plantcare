package be.howest.plantcare.screens.start

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class StartViewModel : ViewModel() {

    // Added "click" to the variable names because the login/register viewModels will have the event eventLogin/Register aswell.

    private val _eventLoginClick = MutableLiveData<Boolean>()
    val eventLoginClick: LiveData<Boolean>
        get() = _eventLoginClick

    private val _eventRegisterClick = MutableLiveData<Boolean>()
    val eventRegisterClick: LiveData<Boolean>
        get() = _eventRegisterClick

    init {

    }

    fun onLogin() {
        _eventLoginClick.value = _eventLoginClick.value != true
    }

    fun onRegister() {
        _eventRegisterClick.value = _eventRegisterClick.value != true
    }
}