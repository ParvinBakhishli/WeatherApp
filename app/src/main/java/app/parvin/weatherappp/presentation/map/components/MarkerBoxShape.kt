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
                moveTo(0f, size.height / 2f)
                arcTo(
                    rect = Rect(
                        offset = Offset(0f,0f),
                        size = Size(size.width / 2f, size.height * 0.9f / 2f)
                    ),
                    startAngleDegrees = 180f,
                    sweepAngleDegrees = 90f,
                    forceMoveTo = false
                )
                lineTo(size.width / 2, 0f)
                arcTo(
                    rect = Rect(
                        offset = Offset(size.width / 2f, 0f),
                        size = Size(size.width / 2f, size.height * 0.9f / 2f)
                    ),
                    startAngleDegrees = 270f,
                    sweepAngleDegrees = 90f,
                    forceMoveTo = false
                )
                arcTo(
                    rect = Rect(
                        offset = Offset(size.width / 2f,size.height * 0.9f / 2f),
                        size = Size(size.width / 2f, size.height * 0.9f / 2f)
                    ),
                    startAngleDegrees = 0f,
                    sweepAngleDegrees = 90f,
                    forceMoveTo = false
                )
                lineTo(size.width / 2f + triangleBottomWidth/2f, size.height * 0.9f)
                lineTo(size.width / 2f, size.height)
                lineTo(size.width / 2f - triangleBottomWidth / 2f, size.height * 0.9f)
                arcTo(
                    rect = Rect(
                        offset = Offset(0f,size.height * 0.9f / 2f),
                        size = Size(size.width / 2f, size.height * 0.9f / 2f)),
                    startAngleDegrees = 90f,
                    sweepAngleDegrees = 90f,
                    forceMoveTo = false
                )
                close()
            }
        )
    }

}