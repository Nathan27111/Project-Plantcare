package be.howest.plantcare.network

import com.squareup.moshi.Json
import java.time.LocalDate
import java.time.LocalDateTime

data class PlantUpdateProperty(
    @Json(name = "room_id")
    val roomId: Int,
    @Json(name = "needs_water") val needsWater: Int,
    @Json(name = "last_watered_at") val lastWateredAt: String,
    @Json(name = "plant_description") val plantDescription: String,
    @Json(name = "flowers") val flowers: Int,
    @Json(name = "height") val height: Int,
    @Json(name = "is_blooming") val isBlooming: Boolean
)