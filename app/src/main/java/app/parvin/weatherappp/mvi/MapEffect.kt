package app.parvin.weatherappp.mvi


sealed class MapEffect {
    data object ShowErrorDialog : MapEffect()
}