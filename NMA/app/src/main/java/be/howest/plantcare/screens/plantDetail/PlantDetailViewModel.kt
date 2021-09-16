package be.howest.plantcare.screens.plantDetail

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import androidx.room.Room
import be.howest.plantcare.database.TokenDatabaseDao
import be.howest.plantcare.network.PlantCareApi
import be.howest.plantcare.network.PlantProperty
import be.howest.plantcare.network.PlantUpdateProperty
import be.howest.plantcare.network.RoomProperty
import kotlinx.coroutines.launch
import java.lang.Exception
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class PlantDetailViewModel(val database: TokenDatabaseDao, application: Application) :
    AndroidViewModel(application) {

    private val _plant = MutableLiveData<PlantProperty>()
    val plant: LiveData<PlantProperty>
        get() = _plant

    private val _room = MutableLiveData<RoomProperty>()
    val room: LiveData<RoomProperty>
        get() = _room

    private val _eventIsBloomingClick = MutableLiveData<Boolean>()
    val eventIsBlooming: LiveData<Boolean>
    get() = _eventIsBloomingClick

    private val _eventRoomClick = MutableLiveData<Boolean>()
    val eventRoomClick: LiveData<Boolean>
        get() = _eventRoomClick

    private val _eventWaterClick = MutableLiveData<Boolean>()
    val eventWaterClick: LiveData<Boolean>
        get() = _eventWaterClick

    private val _eventDoneClick = MutableLiveData<Boolean>()
    val eventDoneClick: LiveData<Boolean>
        get() = _eventDoneClick

    private val _eventDeleteClick = MutableLiveData<Boolean>()
    val eventDeleteClick: LiveData<Boolean>
    get() = _eventDeleteClick

    private val _token = MutableLiveData<String>()
    val token: LiveData<String>
        get() = _token

    private val _navigate = MutableLiveData<Boolean>()
    val navigate: LiveData<Boolean>
        get() = _navigate

    private val _confirmed = MutableLiveData<Boolean>()
    val confirmed: LiveData<Boolean>
        get() = _confirmed

    init {
        viewModelScope.launch {
            getToken() }
    }

    fun getPlant(plantId: Int){
        Log.i("token", _token.value.toString())
        Log.i("token.value", token.value.toString())
        viewModelScope.launch {
            try{
                val response = PlantCareApi.retrofitService.getPlant("Bearer ${_token.value}", plantId)
                _plant.value = response
                _eventIsBloomingClick.value = response.isBlooming
            }
            catch (e: Exception){
                Log.e("GetPlantError", e.message.toString())
                Log.e("GetPlantError", e.cause.toString())
            }
        }
    }

    fun getRoom(){
        val roomId = _plant.value?.roomId
        viewModelScope.launch {
            try {
                val response =
                    roomId?.let { PlantCareApi.retrofitService.getRoom("Bearer ${_token.value}", it) }
                _room.value = response
            }
            catch (e: Exception){
                Log.e("GetRoomError", e.message.toString())
                Log.e("GetRoomError", e.cause.toString())
            }
        }
    }

    fun onIsBloomingClick(){
        _eventIsBloomingClick.value = _eventIsBloomingClick.value != true
    }

    fun onRoomClick(){
        _eventRoomClick.value = _eventRoomClick.value != true
    }

    fun onDeleteClick(){
        _eventDeleteClick.value = _eventDeleteClick.value != true
    }

    fun deletePlant(){
        val plantId = plant.value?.plantId
        viewModelScope.launch {
            try {
                PlantCareApi.retrofitService.deletePlant("Bearer ${_token.value}", plantId!!)
                _confirmed.value = true
                _confirmed.value = false
            } catch (e: Exception){
                Log.e("DeletePlantError", e.message.toString())
                Log.e("DeletePlantError", e.cause.toString())
            }
        }
    }

    fun onWaterPlant(){
        val currentDateTime = LocalDateTime.now().toString()
        val localDateTime = LocalDateTime.parse(currentDateTime);
        val format: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
        val stringDateTime = format.format(localDateTime)
        val wateredPlant = PlantUpdateProperty(
            _plant.value!!.roomId, _plant.value!!.needsWater,
        stringDateTime, _plant.value!!.plantDescription, _plant.value!!.flowers, _plant.value!!.height, _plant.value!!.isBlooming)
        viewModelScope.launch {
            try {
                val response = PlantCareApi.retrofitService.changePlant("Bearer ${_token.value}", wateredPlant, _plant.value!!.plantId )
                _plant.value = response

            } catch (e:Exception){
                Log.e("ChangePlantError", e.message.toString())
                Log.e("ChangePlantError", e.cause.toString())
            }
        }
    }

    fun doneButtonClick(){
        _eventDoneClick.value = _eventDoneClick.value != true
    }

    fun onDoneClick(description: String, flowers: Int, height: Int){
        val changedPlant = PlantUpdateProperty(_plant.value!!.roomId, _plant.value!!.needsWater, _plant.value!!.lastWateredAt, description, flowers, height, _eventIsBloomingClick.value!!)
        viewModelScope.launch {
            try {
                val response = PlantCareApi.retrofitService.changePlant(
                    "Bearer ${_token.value}",
                    changedPlant,
                    _plant.value!!.plantId
                )
                _navigate.value = true
            }
            catch (e: Exception){
                Log.e("ChangePlantError", e.message.toString())
                Log.e("ChangePlantError", e.cause.toString())
            }
        }
    }

    private suspend fun getToken() {
        viewModelScope.launch {
         _token.value =  database.get().token
            Log.i("token.value", token.value.toString())
        }

    }
}