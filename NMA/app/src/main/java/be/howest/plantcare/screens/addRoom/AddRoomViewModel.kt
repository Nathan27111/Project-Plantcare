package be.howest.plantcare.screens.addRoom

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import be.howest.plantcare.database.TokenDatabaseDao
import be.howest.plantcare.network.AddRoomProperty
import be.howest.plantcare.network.PlantCareApi
import be.howest.plantcare.network.RoomProperty
import be.howest.plantcare.network.UserProperty
import kotlinx.coroutines.launch

class AddRoomViewModel(val database: TokenDatabaseDao, application: Application) : AndroidViewModel(application) {

    private val _eventAddRoom = MutableLiveData<Boolean>()
    val eventAddRoom: LiveData<Boolean>
        get() = _eventAddRoom

    private val _token = MutableLiveData<String>()
    val token: LiveData<String>
        get() = _token

    private val _user = MutableLiveData<UserProperty>()
    val user: LiveData<UserProperty>
    get() = _user

    private val _navigate = MutableLiveData<Boolean>()
    val navigate: LiveData<Boolean>
        get() = _navigate

    init {
        viewModelScope.launch {
            getToken()
        }
    }

    fun onAddRoom(){
        _eventAddRoom.value = _eventAddRoom.value != true
    }
    fun addRoom(roomDescription: String){
        val newRoom = AddRoomProperty(_user.value!!.userId, roomDescription)
        viewModelScope.launch { add(newRoom) }
    }

    private suspend fun getToken(){
        viewModelScope.launch {
            _token.value = database.get().token
            _user.value = PlantCareApi.retrofitService.getLoggedInUser("Bearer ${token.value!!}")
        }
    }

    suspend fun add(newRoom: AddRoomProperty){

        viewModelScope.launch {
            try {
                val response = PlantCareApi.retrofitService.addRoom("Bearer ${token.value}", newRoom)
                _navigate.value = true
            }
            catch (e : Exception){
                Log.e("AddRoomError", "Error: ${e.message}")
                Log.e("AddRoomError", "${e.cause}")
            }
        }
    }

}