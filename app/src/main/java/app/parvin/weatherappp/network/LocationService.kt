package app.parvin.weatherappp.network

import app.parvin.weatherappp.data.model.MapResponseDto
import retrofit2.http.GET
import retrofit2.http.Query

interface LocationService {

    @GET("json")
    suspend fun getLocation(
        @Query("address") address: String,
    ) : MapResponseDto
}
