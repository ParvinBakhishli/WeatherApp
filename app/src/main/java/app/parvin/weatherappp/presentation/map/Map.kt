package app.parvin.weatherappp.presentation.map

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.GoogleMapComposable
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.rememberCameraPositionState

@Composable
fun Map(
    modifier: Modifier = Modifier,
    mapContent: @Composable @GoogleMapComposable () -> Unit = {},
) {
    val cameraPosition = rememberCameraPositionState()
    val baku = LatLng(40.409264, 49.867092)

    LaunchedEffect(Unit) {
        cameraPosition.animate(
            CameraUpdateFactory
                .newCameraPosition(CameraPosition.fromLatLngZoom(baku, 8f))
        )
    }

    GoogleMap(
        modifier = modifier,
        cameraPositionState = cameraPosition,
        properties = MapProperties(
            isMyLocationEnabled = true
        ),
        content = mapContent
    )
}