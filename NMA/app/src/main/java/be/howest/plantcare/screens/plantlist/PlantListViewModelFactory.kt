package be.howest.plantcare.screens.plantlist

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import be.howest.plantcare.database.TokenDatabaseDao
import be.howest.plantcare.screens.start.StartViewModel
import java.lang.IllegalArgumentException

class PlantListViewModelFactory(
    private val dataSource: TokenDatabaseDao,
    private val application: Application
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PlantListViewModel::class.java)) {
            return PlantListViewModel(dataSource, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}