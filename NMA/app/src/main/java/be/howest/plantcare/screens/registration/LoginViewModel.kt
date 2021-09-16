package be.howest.plantcare.screens.registration

import android.app.Application
import android.provider.SyncStateContract.Helpers.insert
import android.util.Log
import androidx.lifecycle.*
import be.howest.plantcare.database.TokenDatabaseDao
import be.howest.plantcare.database.TokenKeeper
import be.howest.plantcare.network.LoginProperty
import be.howest.plantcare.network.PlantCareApi
import kotlinx.coroutines.launch
import java.lang.Exception
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class LoginViewModel(val database: TokenDatabaseDao, application: Application): AndroidViewModel(application) {

    private val _eventLogin = MutableLiveData<Boolean>()
    val eventLogin: LiveData<Boolean>
        get() = _eventLogin

    private val _bearerToken = MutableLiveData<String>()
    val bearerToken: LiveData<String>
        get() = _bearerToken

    init {

    }

    fun login(email: String, password: String) {
        viewModelScope.launch {
            try {
                val loginData = LoginProperty(email, password)
                val response = PlantCareApi.retrofitService.loginUser(loginData)
                _bearerToken.value = response.accessToken
                putTokenInDataBase(response.accessToken)
                viewModelScope.launch {
                    Log.i("dbToken", database.get().toString())
                }
                Log.i("login token value", _bearerToken.value.toString())
            }
            catch (e : Exception){
                Log.i("LoginError", "Failure:  ${e.message}")
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
            insert(tokenKeeper)
        }
    }

    private suspend fun insert(token: TokenKeeper){
        database.insert(token)
    }


    fun onLogin() {
        _eventLogin.value = _eventLogin.value != true
    }
}