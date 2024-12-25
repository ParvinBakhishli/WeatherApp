package app.parvin.weatherappp.presentation.map.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import app.parvin.weatherappp.domain.model.City


@Composable
fun SearchedCityOptions(
    cities: List<City>,
    onClicked : (city:City) -> Unit
) {
    LazyColumn {
        items(cities) { city ->
            Text(
                text = city.name,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 3.dp)
                    .background(
                        color = Color.White,
                        shape = RoundedCornerShape(6.dp)
                    )
                    .border(width = 0.5.dp, color = Color.LightGray, shape = RoundedCornerShape(6.dp))
                    .padding(horizontal = 10.dp, vertical = 8.dp)
                    .clickable { onClicked(city) },
                style = TextStyle(color = Color.DarkGray)
            )
        }
    }
}

@Preview
@Composable
private fun Demo() {
//    SearchedCityOptions(
//        listOf(
//            City("Baku Azerbaijan", LatLng(0.0, 0.0)),
//            City("Baku", LatLng(0.0, 0.0)),
//            City("Baku Az", LatLng(0.0, 0.0))
//        )
//    ){}
//
}