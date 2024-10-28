package app.parvin.weatherappp.presentation.home

import androidx.compose.animation.core.EaseOutBounce
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.animateScrollBy
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import app.parvin.weatherappp.presentation.home.components.DailyWeatherItem
import app.parvin.weatherappp.presentation.home.components.HourlyWeatherItem
import app.parvin.weatherappp.util.NotificationHelper
import kotlin.math.roundToInt

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    location: String
) {
    val viewModel = hiltViewModel<HomeViewModel>()
    val context = LocalContext.current


    val hourlyDataList by viewModel.todayData.collectAsStateWithLifecycle()
    val dailyDataList by viewModel.dailyData.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                navigationIcon = {
                    IconButton(onClick = {
                        //helper.showNotification(Pair(dailyDataList[0].averageTemperature.first.roundToInt(),dailyDataList[0].averageTemperature.second.roundToInt()))
                    }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Default.ArrowBack,
                            contentDescription = null
                        )
                    }
                },
                title = {
                    Text(text = location)
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(color = Color.White)
                .padding(top = 32.dp)
        ) {
            Text(text = "Today", fontSize = 16.sp, modifier = Modifier.padding(start = 20.dp))
            Spacer(modifier = Modifier.height(20.dp))

            val state = rememberLazyListState()
            LaunchedEffect(key1 = state, key2 = hourlyDataList) {
                val itemIndex = hourlyDataList.indexOfFirst {
                    it?.isNow == true
                }

                if (itemIndex != -1) {
                    val offset = state.layoutInfo.run {
                        val totalItemWidth = visibleItemsInfo.first().size * itemIndex
                        val totalItemSpacing = mainAxisItemSpacing * itemIndex

                        beforeContentPadding + totalItemWidth + totalItemSpacing
                    }
                    state.animateScrollBy(
                        offset.toFloat(),
//                        animationSpec = spring(
//                            dampingRatio = Spring.DampingRatioMediumBouncy
//                        )
                        animationSpec = tween(
                            durationMillis = 3000,
                            delayMillis = 100,
                            easing = FastOutSlowInEasing
                        )
                    )
                    //state.animateScrollToItem(itemIndex, )
                }
            }

            LazyRow(
                state = state,
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(6.dp),
                contentPadding = PaddingValues(horizontal = 20.dp)
            ) {
                items(hourlyDataList) {
                    if (it != null) {
                        HourlyWeatherItem(
                            time = if (it.hour == "12 AM") "00 AM" else it.hour,
                            weatherIcon = if (it.isDay == 1) it.weatherType.iconDay else it.weatherType.iconNight,
                            temperature = "${it.temperature.roundToInt()}°C",
                            selected = it.isNow
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
                itemsIndexed(dailyDataList, key = { _, it -> it.date }) { i, item ->
                    DailyWeatherItem(
                        dayOfWeek = item.dayOfWeek,
                        date = item.date,
                        temperature = "${item.averageTemperature.first.roundToInt()}°C-${item.averageTemperature.second.roundToInt()}°C",
                        weatherIcon = item.weatherCode.iconDay,
                        modifier = Modifier.animateItem(
                            placementSpec = tween(
                                durationMillis = 300,
                                delayMillis = i * 100
                            )
                        )
                    )
                }
            }
        }
    }
}


@Preview
@Composable
private fun Prev() {
    HomeScreen(location = "Gotham")
}