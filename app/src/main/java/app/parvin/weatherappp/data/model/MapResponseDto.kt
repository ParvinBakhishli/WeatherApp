package app.parvin.weatherappp.data.model

import kotlinx.serialization.Serializable


@Serializable
data class MapResponseDto(
    val results: List<ResultDto>?,
    val status: String
)
