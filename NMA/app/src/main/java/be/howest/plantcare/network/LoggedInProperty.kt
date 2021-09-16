package be.howest.plantcare.network

import com.squareup.moshi.Json

data class LoggedInProperty (
    @Json(name="access_token") val accessToken: String,
    @Json(name="token_type") val tokenType: String,
    @Json(name ="expires_in") val expiresIn: Int,
    @Json(name="user") val user: UserProperty
        )