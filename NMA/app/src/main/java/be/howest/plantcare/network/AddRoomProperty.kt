package be.howest.plantcare.network

import com.squareup.moshi.Json

data class AddRoomProperty(
    @Json(name = "user_id") val userId: Int,
    @Json(name = "room_description") val roomDescription: String
)
