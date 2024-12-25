package app.parvin.weatherappp.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class ResultDto(
    @SerialName("formatted_address") val address: String,
    @SerialName("geometry") val location: GeometryDto
)
