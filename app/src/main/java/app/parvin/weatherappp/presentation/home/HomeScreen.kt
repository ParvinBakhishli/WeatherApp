package app.parvin.weatherappp.presentation.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.util.fastRoundToInt
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewModelScope
import app.parvin.weatherappp.R
import app.parvin.weatherappp.presentation.home.components.DailyWeatherItem
import app.parvin.weatherappp.presentation.home.components.HourlyWeatherItem
import kotlinx.coroutines.flow.collectLatest
import kotlin.math.roundToInt

@Composable
fun HomeScreen(
    location: String
) {
    val viewModel = hiltViewModel<HomeViewModel>()
    viewModel.fetchTodayHourlyData()
    viewModel.fetchDailyData()

    val hourlyDataList = viewModel.todayData.collectAsState().value
    val dailyDataList = viewModel.dailyData.collectAsState().value

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White)
            .padding(top = 32.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(imageVector = Icons.Default.ArrowBack, contentDescription = null)
            Text(text = location, fontSize = 20.sp, fontWeight = FontWeight.Normal)
            Text(text = "     ")
        }
        Spacer(modifier = Modifier.height(32.dp))
        Text(text = "Today", fontSize = 16.sp, modifier = Modifier.padding(start = 20.dp))
        Spacer(modifier = Modifier.height(20.dp))
        LazyRow(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(6.dp),
            contentPadding = PaddingValues(horizontal = 20.dp)
        ) {
            items(hourlyDataList) {
                if (it != null) {
                    HourlyWeatherItem(
                        time = if (it.hour == "12 AM") "00 AM" else it.hour,
                        weatherIcon = it.weatherType.iconRes,
                        temperature = "${it.temperature.roundToInt()}°C",
                        backgroundColor = Color.White
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(24.dp))
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
                .weight(1f),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            items(dailyDataList) {
                DailyWeatherItem(
                    dayOfWeek = "Tomorrow",
                    date = it.date,
                    temperature = "${it.averageTemperature.first}-${it.averageTemperature.second}",
                    weatherIcon = it.weatherCode.iconRes
                )
            }

        }

    }
}


@Preview
@Composable
private fun Prev() {
    HomeScreen(location = "Gotham")
}