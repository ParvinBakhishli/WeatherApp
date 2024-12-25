package app.parvin.weatherappp.presentation.map.components

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection


class MarkerBoxShape(
    val triangleBottomWidth : Float = 20f,
) : Shape {
    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {
        return Outline.Generic(
            Path().apply {
                moveTo(0f, size.height / 4f)
                arcTo(
                    rect = Rect(
                        offset = Offset(0f,0f),
                        size = Size(size.width / 4f, size.height / 4f)
                    ),
                    startAngleDegrees = 180f,
                    sweepAngleDegrees = 90f,
                    forceMoveTo = false
                )
                lineTo(size.width * 3f / 4, 0f)
                arcTo(
                    rect = Rect(
                        offset = Offset(size.width * 3f / 4, 0f),
                        size = Size(size.width / 4f, size.height / 4f)
                    ),
                    startAngleDegrees = 270f,
                    sweepAngleDegrees = 90f,
                    forceMoveTo = false
                )
                arcTo(
                    rect = Rect(
                        offset = Offset(size.width * 3f/ 4,(size.height * 0.9f) *3f / 4),
                        size = Size(size.width / 4f, size.height / 4f)
                    ),
                    startAngleDegrees = 0f,
                    sweepAngleDegrees = 90f,
                    forceMoveTo = false
                )
                lineTo(size.width / 2f + triangleBottomWidth/2f, size.height * 0.93f)
                lineTo(size.width / 2f, size.height)
                lineTo(size.width / 2f - triangleBottomWidth / 2f, size.height * 0.93f)
                arcTo(
                    rect = Rect(
                        offset = Offset(0f,(size.height * 0.9f) * 3f / 4),
                        size = Size(size.width / 4f, size.height / 4f)
                    ),
                    startAngleDegrees = 90f,
                    sweepAngleDegrees = 90f,
                    forceMoveTo = false
                )
                close()
            }
        )
    }

}