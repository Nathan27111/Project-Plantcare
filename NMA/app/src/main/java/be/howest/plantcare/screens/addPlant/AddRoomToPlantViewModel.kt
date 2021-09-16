package be.howest.plantcare.screens.addPlant

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import be.howest.plantcare.database.TokenDatabaseDao
import be.howest.plantcare.network.PlantCareApi
import be.howest.plantcare.network.PlantUpdateProperty
import be.howest.plantcare.network.RoomProperty
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class AddRoomToPlantViewModel(val database: TokenDatabaseDao, application: Application) : AndroidViewModel(application) {

    private val _eventRoomClicked= MutableLiveData<Int>()
    val eventRoomClicked: LiveData<Int>
    get() = _eventRoomClicked

    private val _eventAddPlant= MutableLiveData<Boolean>()
    val eventAddPlant: LiveData<Boolean>
        get() = _eventAddPlant

    private val _rooms= MutableLiveData<List<RoomProperty>>()
    val rooms: LiveData<List<RoomProperty>>
        get() = _rooms

    private val _token= MutableLiveData<String>()
    val token: LiveData<String>
        get() = _token

    private val _navigate = MutableLiveData<Boolean>()
    val navigate: LiveData<Boolean>
    get() = _navigate

    init {
        viewModelScope.launch {
            getToken()
        }
    }

    fun onRoomClicked(roomId: Int){
        _eventRoomClicked.value = roomId
    }

    fun onAdd(){
        _eventAddPlant.value = _eventAddPlant.value != true
    }

    fun onNavigate(){
        _navigate.value = _navigate.value != true
    }

    suspend fun getToken(){
        viewModelScope.launch {
            _token.value = database.get().token
            viewModelScope.launch {
                getRooms()
            }
        }
    }

    suspend fun getRooms(){
        val token = token.value
        viewModelScope.launch {
            try{
                val response = PlantCareApi.retrofitService.getRooms("Bearer $token")
                _rooms.value = response
            }
            catch (e: Exception){
                Log.e("GetRoomsError", e.message.toString())
                Log.e("GetRoomsError", e.cause.toString())
            }
        }
    }

    fun addPlant(water: Int, plantDescription: String, flowers: Int, height: Int, isBlooming: Boolean){
        val format: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
        val currentTime = LocalDateTime.now()
        val formattedTime = currentTime.format(format)
        val plant = PlantUpdateProperty(_eventRoomClicked.value!!, water, formattedTime, plantDescription, flowers, height, isBlooming)
        viewModelScope.launch {
            try {
                val response = PlantCareApi.retrofitService.addPlant("Bearer ${token.value}", plant)
                _navigate.value = true
            }
            catch (e: Exception){
                Log.e("AddPlantError", e.message.toString())
                Log.e("AddPlantError", e.cause.toString())
            }
        }
    }
}