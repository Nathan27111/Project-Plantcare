package be.howest.plantcare.network

import com.squareup.moshi.Json

data class RegisterProperty (
    @Json(name = "first_name") val firstName: String?,
    @Json(name = "last_name") val lastName: String?,
    @Json(name = "email") val email: String?,
    @Json(name = "password") val password: String?
)
