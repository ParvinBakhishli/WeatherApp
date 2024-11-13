package app.parvin.weatherappp.domain.model

import androidx.compose.ui.graphics.vector.ImageVector
import com.google.android.gms.maps.model.LatLng

data class CityWeather(
    val location: LatLng,
    val name: String,
    val temperature: Int,
    val weatherIcon: Int
)
