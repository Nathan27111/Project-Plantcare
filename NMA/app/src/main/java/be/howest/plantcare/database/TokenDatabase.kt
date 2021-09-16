package be.howest.plantcare.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [TokenKeeper::class], version = 2, exportSchema = false)
abstract class TokenDatabase : RoomDatabase() {

    abstract val tokenDatabaseDao: TokenDatabaseDao

    companion object {
        @Volatile
        private var INSTANCE: TokenDatabase? = null

        fun getInstance(context: Context): TokenDatabase {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        TokenDatabase::class.java,
                        "sleep_history_database"
                    ).fallbackToDestructiveMigration().build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}