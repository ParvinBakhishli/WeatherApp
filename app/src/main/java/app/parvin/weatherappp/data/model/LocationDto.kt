package app.parvin.weatherappp.data.model

import kotlinx.serialization.Serializable


@Serializable
data class LocationDto(
    val lat: Double,
    val lng: Double
)
