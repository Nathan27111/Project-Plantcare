package be.howest.plantcare.screens.registration

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import be.howest.plantcare.database.TokenDatabaseDao
import be.howest.plantcare.database.TokenKeeper
import be.howest.plantcare.network.LoginProperty
import be.howest.plantcare.network.PlantCareApi
import be.howest.plantcare.network.RegisterProperty
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class RegisterViewModel(val database: TokenDatabaseDao, application: Application): AndroidViewModel(application) {

    private val _eventRegister = MutableLiveData<Boolean>()
    val eventRegister: LiveData<Boolean>
        get() = _eventRegister

    private val _bearerToken = MutableLiveData<String>()
    val bearerToken: LiveData<String>
        get() = _bearerToken

    private val _email = MutableLiveData<String>()
    val email: LiveData<String>
        get() = _email

    private val _password = MutableLiveData<String>()
    val password: LiveData<String>
        get() = _password

    private val _response = MutableLiveData<String>()
    val response: LiveData<String>
        get() = _response

    private val _isLoggedIn = MutableLiveData<Boolean>()
    val isLoggedIn: LiveData<Boolean>
        get() = _isLoggedIn

    init {

    }

    fun register(firstName: String, lastName: String, email: String, password: String) {
        viewModelScope.launch {
            try {
                val userSend = RegisterProperty(firstName, lastName, email, password)
                val response = PlantCareApi.retrofitService.registerUser(userSend)
                _email.value = response.email
                _password.value = password
            } catch (e: Exception) {
                _response.value = "Failure: ${e.message}"
            }
        }
    }

    fun login() {
        viewModelScope.launch {
            try {
                val loginData = LoginProperty(_email.value.toString(), _password.value.toString())
                val response = PlantCareApi.retrofitService.loginUser(loginData)
                _bearerToken.value = response.accessToken

                putTokenInDataBase(response.accessToken)
                _isLoggedIn.value = true
            } catch (e: java.lang.Exception) {
                Log.e("LoginError", "Failure:  ${e.message}")
            }
        }
    }

    private suspend fun putTokenInDataBase(token: String){
        val currentDateTime = LocalDateTime.now().toString()
        val localDateTime = LocalDateTime.parse(currentDateTime);
        val format: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
        val stringDateTime = format.format(localDateTime)
        val tokenKeeper = TokenKeeper(token, stringDateTime)
        viewModelScope.launch {
            try {
                insert(tokenKeeper)
            }
            catch (e: Exception){
                Log.e("RoomError", e.message.toString())
                Log.e("RoomError", e.cause.toString())
            }
        }
    }

    private suspend fun insert(token: TokenKeeper){
        database.insert(token)
    }

    fun onRegister() {
        _eventRegister.value = _eventRegister.value != true
    }

    fun onLogin() {
        _isLoggedIn.value = _isLoggedIn.value != true
    }
}