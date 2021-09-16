package be.howest.plantcare.screens.changeRoom

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import be.howest.plantcare.database.TokenDatabaseDao
import be.howest.plantcare.network.PlantCareApi
import be.howest.plantcare.network.PlantProperty
import be.howest.plantcare.network.PlantUpdateProperty
import be.howest.plantcare.network.RoomProperty
import kotlinx.coroutines.launch
import java.lang.Exception

class ChangeRoomViewModel(val database: TokenDatabaseDao, application: Application) :
    AndroidViewModel(application) {

    private val _token = MutableLiveData<String>()
    val token: LiveData<String>
        get() = _token
    private val _rooms = MutableLiveData<List<RoomProperty>>()
    val rooms: LiveData<List<RoomProperty>>
        get() = _rooms
    private val _selectedRoom = MutableLiveData<Int>()
    val selectedRoom: LiveData<Int>
        get() = _selectedRoom
    private val _eventChangeClick = MutableLiveData<Boolean>()
    val eventChangeClick: LiveData<Boolean>
        get() = _eventChangeClick
    private val _plant = MutableLiveData<PlantProperty>()
    val plant: LiveData<PlantProperty>
        get() = _plant
    private val _isChanged = MutableLiveData<Boolean>()
    val isChanged: LiveData<Boolean>
        get() = _isChanged


    init {
        viewModelScope.launch {
            getToken()
        }
    }

    fun getRooms(){
        viewModelScope.launch {
            try {
                val response = PlantCareApi.retrofitService.getRooms("Bearer ${_token.value}")
                _rooms.value = response
            }
            catch (e: Exception){
                Log.e("GetRoomsError", e.message.toString())
                Log.e("GetRoomsError", e.cause.toString())
            }
        }
    }

    fun getPlant(plantId: Int){
        viewModelScope.launch {
            try{
                val response = PlantCareApi.retrofitService.getPlant(
                    "Bearer ${_token.value}",
                    plantId
                )
                _plant.value = response
            }
            catch (e: Exception){
                Log.e("GetPlantError", e.message.toString())
                Log.e("GetPlantError", e.cause.toString())
            }
        }
    }


    fun onRoomClick(roomId: Int){
        _selectedRoom.value = roomId
    }

    suspend fun getToken(){
        viewModelScope.launch {
            _token.value = database.get().token
        }
    }

    fun onChangeClick(){
        val water = _plant.value?.needsWater
        val lastWateredAt = _plant.value?.lastWateredAt
        val plantDescription = _plant.value?.plantDescription
        val flowers = _plant.value?.flowers
        val height = _plant.value?.height
        val isBlooming = _plant.value?.isBlooming
        val plantUpdateProperty = PlantUpdateProperty(_selectedRoom.value!!,
            water!!,lastWateredAt!!,plantDescription!!,flowers!!,height!!,isBlooming!!)
        viewModelScope.launch {
            try {
                val response = PlantCareApi.retrofitService.changePlant("Bearer ${_token.value}",
                    plantUpdateProperty, _plant.value!!.plantId)
                _isChanged.value = true
            }
            catch (e: Exception){
                Log.e("ChangePlantError", e.message.toString())
                Log.e("ChangePlantError", e.cause.toString())
            }
        }
    }
}