package app.parvin.weatherappp.presentation.map.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import app.parvin.weatherappp.R
import app.parvin.weatherappp.domain.model.City
import app.parvin.weatherappp.domain.model.CityWeather
import app.parvin.weatherappp.domain.model.WeatherType
import com.airbnb.lottie.compose.LottieClipSpec
import com.google.android.gms.maps.model.LatLng


@Composable
fun WeatherMarker(
    city: CityWeather
) {
    MarkerBox {
        Column(
            modifier = Modifier
                .background(
                    color = Color.White,
                    shape = RoundedCornerShape(16.dp)
                )
                .padding(vertical = 8.dp, horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = city.name,
                fontSize = 14.sp,
                color = Color.DarkGray
            )
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(city.weatherIcon),
                    contentDescription = null,
                    modifier = Modifier.size(42.dp),
                    tint = Color.Unspecified
                )
                Text(text = "${city.temperature}°", color = Color.Black)
            }
        }
    }
}

@Preview
@Composable
private fun Demo() {
    WeatherMarker(CityWeather(LatLng(40.0,49.0),"Baku",23, R.drawable.ic_sun))
}
