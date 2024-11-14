package app.parvin.weatherappp.presentation.map.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp


@Composable
fun MarkerBox(
    modifier: Modifier = Modifier,
    outlineWidth: Dp = 0.5.dp,
    outlineColor: Color = Color.DarkGray,
    content: @Composable BoxScope.() -> Unit,
) {
    var shape by remember { mutableStateOf(MarkerBoxShape())}

    Box(
        modifier = modifier
            .onSizeChanged {
                shape = MarkerBoxShape(
                    triangleBottomWidth = it.width * 0.1f
                )
            }
            .clip(shape)
            .border(outlineWidth, outlineColor, shape)
            .background(Color.White),
    ) {
        Box() {
            content()
        }
    }
}

@Preview
@Composable
private fun Demo() {
    Surface(color = Color.Blue, modifier = Modifier.fillMaxSize()) {
        Box{
            MarkerBox (
                modifier = Modifier.size(200.dp)
            ){

            }
        }
    }
}