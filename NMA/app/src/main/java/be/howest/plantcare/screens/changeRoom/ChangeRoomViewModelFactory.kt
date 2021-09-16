package be.howest.plantcare.screens.changeRoom

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import be.howest.plantcare.database.TokenDatabaseDao
import be.howest.plantcare.screens.addRoom.AddRoomViewModel
import be.howest.plantcare.screens.registration.RegisterViewModel
import java.lang.IllegalArgumentException

class ChangeRoomViewModelFactory(
    private val dataSource: TokenDatabaseDao,
    private val application: Application
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ChangeRoomViewModel::class.java)) {
            return ChangeRoomViewModel(dataSource, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}