package be.howest.plantcare.database

import android.media.session.MediaSession
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface TokenDatabaseDao {
    @Insert
    suspend fun insert(token: TokenKeeper)

    @Query("SELECT * from token_table ORDER BY created_at DESC LIMIT 1")
    suspend fun get(): TokenKeeper


}