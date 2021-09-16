package be.howest.plantcare.screens.plantlist

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import be.howest.plantcare.database.TokenDatabaseDao
import be.howest.plantcare.database.TokenKeeper
import be.howest.plantcare.network.PlantCareApi
import be.howest.plantcare.network.PlantProperty
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class PlantListViewModel(val database: TokenDatabaseDao, application: Application) :
    AndroidViewModel(application) {

    private val _token = MutableLiveData<String>()
    val token: LiveData<String>
        get() = _token

    private val _plants = MutableLiveData<List<PlantProperty>>()
    val plants: LiveData<List<PlantProperty>>
        get() = _plants

    private val _refresh = MutableLiveData<Boolean>()
    val refresh: LiveData<Boolean>
        get() = _refresh

    private val _navigateToPlantDetail = MutableLiveData<Int>()
    val navigateToPlantDetail: LiveData<Int>
        get() = _navigateToPlantDetail

    init {
        viewModelScope.launch {
            setToken()
        }
    }

    fun getPlants() {
        Log.i("hello there", "general kenobi")
        viewModelScope.launch {
            try {
                val response = PlantCareApi.retrofitService.getPlants("Bearer ${_token.value}")
                _plants.value = response;
                Log.i("response", response.toString())
            } catch (e: Exception) {
                Log.e("PlantListError", "Error: ${e.message}")
                Log.e("PlantListError", "${e.cause}")
            }
        }
    }

    fun onPlantClick(plantId: Int) {
        _navigateToPlantDetail.value = plantId
    }

    fun refreshClick(){
        _refresh.value = _refresh.value != true
    }

    fun onPlantDetailNavigated() {
        _navigateToPlantDetail.value = null
    }

    suspend fun setToken() {
        viewModelScope.launch {
            _token.value = database.get().token
        }
    }
}