package be.howest.plantcare.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime

@Entity(tableName = "token_table", primaryKeys = ["token", "created_at"])
data class TokenKeeper(
    @ColumnInfo(name = "token")
    var token: String,
    @ColumnInfo(name = "created_at")
    var createdAt: String
)
