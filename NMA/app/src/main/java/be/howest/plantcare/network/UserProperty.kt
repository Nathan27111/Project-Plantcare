package be.howest.plantcare.network

import com.squareup.moshi.Json

data class UserProperty (
    @Json(name ="user_id") val userId: Int,
    @Json(name = "first_name") val firstName: String,
    @Json(name = "last_name") val lastName: String,
    @Json(name = "email") val email: String
)