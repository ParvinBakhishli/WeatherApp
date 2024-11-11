package app.parvin.weatherappp.mvi


sealed class HomeEffect {
    data object ShowDialog: HomeEffect()
}