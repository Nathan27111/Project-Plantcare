package be.howest.plantcare.network

import com.squareup.moshi.Json

data class LoginProperty(
    @Json(name = "email")
    val email: String,
    @Json(name = "password")
    val password: String
)