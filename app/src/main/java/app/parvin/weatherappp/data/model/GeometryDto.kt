package app.parvin.weatherappp.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class GeometryDto(
    @SerialName("location") val location: LocationDto
)
