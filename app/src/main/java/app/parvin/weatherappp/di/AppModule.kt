package app.parvin.weatherappp.di

import app.parvin.weatherappp.BuildConfig
import app.parvin.weatherappp.domain.repository.WeatherRepository
import app.parvin.weatherappp.network.ApiKeyInterceptor
import app.parvin.weatherappp.network.ApiService
import app.parvin.weatherappp.network.LocationService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.create
import javax.inject.Named

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    private val json = Json {
        ignoreUnknownKeys = true
    }

    @Provides
    fun provideOkhttpClient() = OkHttpClient.Builder()
        .addInterceptor(ApiKeyInterceptor(BuildConfig.MAPS_API_KEY))
        .addInterceptor(
            HttpLoggingInterceptor()
                .setLevel(HttpLoggingInterceptor.Level.BODY)
        )
        .build()

    @Named(WEATHER_CLIENT)
    @Provides
    fun provideRetrofit() = Retrofit.Builder()
        .baseUrl("https://api.open-meteo.com/v1/")
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .build()

    @Named(LOCATION_CLIENT)
    @Provides
    fun provideRetrofitForLocation(client: OkHttpClient) = Retrofit.Builder()
        .baseUrl("https://maps.googleapis.com/maps/api/geocode/")
        .client(client)
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .build()


    @Provides
    fun provideApiService(@Named(WEATHER_CLIENT) client: Retrofit) =
        client.create<ApiService>()

    @Provides
    fun provideApiServiceForMap(@Named(LOCATION_CLIENT) client: Retrofit) =
        client.create<LocationService>()
}

const val WEATHER_CLIENT = "weather_client"
const val LOCATION_CLIENT = "location_client"