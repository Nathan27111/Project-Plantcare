package be.howest.plantcare.network

import be.howest.plantcare.other.LocalDateTimeAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.*
import java.time.LocalDateTime

private const val BASE_URL = "https://whispering-lake-39539.herokuapp.com/api/"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .add(LocalDateTime::class.java, LocalDateTimeAdapter().nullSafe())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()


interface PlantCareApiService {

    @Headers("Content-Type: application/json")
    @POST("register")
    suspend fun registerUser(@Body userProperty: RegisterProperty): UserProperty

    @Headers("Content-Type: application/json")
    @POST("login")
    suspend fun loginUser(@Body loginProperty: LoginProperty): LoggedInProperty

    @GET("user")
    suspend fun getLoggedInUser(@Header("Authorization") bearerToken: String): UserProperty

    @GET("plants")
    suspend fun getPlants(@Header("Authorization") bearerToken: String): List<PlantProperty>

    @GET("rooms")
    suspend fun getRooms(@Header("Authorization") bearerToken: String): List<RoomProperty>

    @GET("plants/{id}")
    suspend fun getPlant(@Header("Authorization") bearerToken: String, @Path("id")plantId: Int): PlantProperty

    @PUT("plants/{id}")
    suspend fun changePlant(@Header("Authorization") bearerToken: String, @Body updatedPlant: PlantUpdateProperty, @Path("id") plantId: Int): PlantProperty

    @GET("rooms/{id}")
    suspend fun getRoom(@Header("Authorization") bearerToken: String, @Path("id") roomId: Int): RoomProperty

    @POST("rooms")
    suspend fun addRoom(@Header("Authorization") bearerToken: String, @Body roomProperty: AddRoomProperty): List<RoomProperty>

    @POST("plants")
    suspend fun addPlant(@Header("Authorization") bearerToken: String, @Body plantUpdateProperty: PlantUpdateProperty): PlantProperty

    @DELETE("plants/{id}")
    suspend fun deletePlant(@Header("Authorization") bearerToken: String, @Path("id") plantId: Int)
}

object PlantCareApi{
    val retrofitService: PlantCareApiService by lazy {
        retrofit.create(PlantCareApiService::class.java)
    }
}