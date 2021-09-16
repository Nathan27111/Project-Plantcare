package be.howest.plantcare.screens.addPlant

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class AddPlantViewModel : ViewModel() {
    private val _eventNext = MutableLiveData<Boolean>()
    val eventNext: LiveData<Boolean>
        get() = _eventNext

    private val _eventIsBloomingClick = MutableLiveData<Boolean>()
    val eventIsBloomingClick: LiveData<Boolean>
        get() = _eventIsBloomingClick

    init{
    }

    fun onNext(){
        _eventNext.value = _eventNext.value != true
    }

    fun onIsBloomingClick(){
        _eventIsBloomingClick.value = _eventIsBloomingClick.value != true
    }
}