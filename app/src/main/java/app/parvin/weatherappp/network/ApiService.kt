package app.parvin.weatherappp.network

import app.parvin.weatherappp.data.model.WeatherResponseDto
import retrofit2.http.GET
import retrofit2.http.Query


interface ApiService {

    @GET("forecast")
    suspend fun getWeatherData(
        @Query("latitude") lat: Double,
        @Query("longitude") long: Double,
        @Query("timezone") timezone: String,
        @Query("forecast_days") days: Int,
        @Query("hourly") hourlyData: List<String>,
        @Query("daily") dailyData: List<String>
    ): WeatherResponseDto

}
