package com.example.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import com.example.ui.theme.GoldBorder
import com.example.ui.theme.GoldDark
import com.example.ui.theme.GoldPrimary
import com.example.ui.theme.NavyBorder

@Composable
fun CircuitBackground(
    modifier: Modifier = Modifier,
    circuitAlpha: Float = 0.35f
) {
    Canvas(modifier = modifier.fillMaxSize()) {
        val w = size.width
        val h = size.height

        val goldLineBrush = Brush.linearGradient(
            colors = listOf(
                GoldPrimary.copy(alpha = circuitAlpha * 0.9f),
                GoldDark.copy(alpha = circuitAlpha * 0.4f),
                NavyBorder.copy(alpha = 0.2f)
            ),
            start = Offset(0f, 0f),
            end = Offset(w, h)
        )

        val strokeWidth = 1.6f

        // Top-left circuit trace
        val path1 = Path().apply {
            moveTo(w * 0.05f, 0f)
            lineTo(w * 0.05f, h * 0.12f)
            lineTo(w * 0.15f, h * 0.20f)
            lineTo(w * 0.35f, h * 0.20f)
        }
        drawPath(path1, brush = goldLineBrush, style = Stroke(width = strokeWidth, cap = StrokeCap.Round))
        drawCircle(
            color = GoldPrimary.copy(alpha = circuitAlpha),
            radius = 3.5f,
            center = Offset(w * 0.35f, h * 0.20f)
        )
        drawCircle(
            color = Color.Black,
            radius = 1.5f,
            center = Offset(w * 0.35f, h * 0.20f)
        )

        // Top-right circuit trace
        val path2 = Path().apply {
            moveTo(w * 0.95f, 0f)
            lineTo(w * 0.95f, h * 0.14f)
            lineTo(w * 0.82f, h * 0.22f)
            lineTo(w * 0.65f, h * 0.22f)
        }
        drawPath(path2, brush = goldLineBrush, style = Stroke(width = strokeWidth, cap = StrokeCap.Round))
        drawCircle(
            color = GoldPrimary.copy(alpha = circuitAlpha),
            radius = 3.5f,
            center = Offset(w * 0.65f, h * 0.22f)
        )

        // Mid-screen circuit traces
        val path3 = Path().apply {
            moveTo(0f, h * 0.45f)
            lineTo(w * 0.18f, h * 0.45f)
            lineTo(w * 0.28f, h * 0.52f)
            lineTo(w * 0.45f, h * 0.52f)
        }
        drawPath(path3, brush = goldLineBrush, style = Stroke(width = strokeWidth, cap = StrokeCap.Round))
        drawCircle(
            color = GoldPrimary.copy(alpha = circuitAlpha),
            radius = 3.5f,
            center = Offset(w * 0.45f, h * 0.52f)
        )

        // Right side horizontal branching
        val path4 = Path().apply {
            moveTo(w, h * 0.48f)
            lineTo(w * 0.80f, h * 0.48f)
            lineTo(w * 0.72f, h * 0.56f)
            lineTo(w * 0.55f, h * 0.56f)
        }
        drawPath(path4, brush = goldLineBrush, style = Stroke(width = strokeWidth, cap = StrokeCap.Round))
        drawCircle(
            color = GoldPrimary.copy(alpha = circuitAlpha),
            radius = 3.5f,
            center = Offset(w * 0.55f, h * 0.56f)
        )

        // Bottom traces
        val path5 = Path().apply {
            moveTo(w * 0.10f, h)
            lineTo(w * 0.10f, h * 0.85f)
            lineTo(w * 0.25f, h * 0.78f)
            lineTo(w * 0.40f, h * 0.78f)
        }
        drawPath(path5, brush = goldLineBrush, style = Stroke(width = strokeWidth, cap = StrokeCap.Round))
        drawCircle(
            color = GoldPrimary.copy(alpha = circuitAlpha),
            radius = 3.5f,
            center = Offset(w * 0.40f, h * 0.78f)
        )
    }
}
