package be.howest.plantcare.screens.addPlant

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import be.howest.plantcare.screens.registration.LoginViewModel
import java.lang.IllegalArgumentException

class AddPlantViewModelFactory() : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AddPlantViewModel::class.java)) {
            return AddPlantViewModel() as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}