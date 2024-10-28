package app.parvin.weatherappp.presentation.home.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import app.parvin.weatherappp.R
import app.parvin.weatherappp.ui.theme.AliceBlue


@Composable
fun HourlyWeatherItem(
    time: String,
    weatherIcon: Int,
    temperature: String,
    selected: Boolean
) {
    Column(
        modifier = Modifier
            .padding(4.dp)
            .shadow(
                elevation = 4.dp,
                shape = RoundedCornerShape(10.dp),
                spotColor = Color.LightGray,
                clip = false
            )
            .background(
                color = if (selected) AliceBlue else Color.White,
                shape = RoundedCornerShape(10.dp)
            )
            .padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = time, fontSize = 14.sp)
        Image(
            painter = painterResource(id = weatherIcon),
            contentDescription = null
        )
        Text(text = temperature, fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
    }

}


@Preview(showSystemUi = true)
@Composable
private fun Prev() {
    HourlyWeatherItem(
        time = "00 PM",
        weatherIcon = R.drawable.ic_sun_cloud,
        temperature = "24°",
        selected = true
    )
}