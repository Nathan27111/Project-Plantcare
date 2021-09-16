package be.howest.plantcare.screens.addPlant

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import be.howest.plantcare.database.TokenDatabaseDao
import be.howest.plantcare.screens.changeRoom.ChangeRoomViewModel
import java.lang.IllegalArgumentException

class AddRoomToPlantViewModelFactory(
    private val dataSource: TokenDatabaseDao,
    private val application: Application
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AddRoomToPlantViewModel::class.java)) {
            return AddRoomToPlantViewModel(dataSource, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}