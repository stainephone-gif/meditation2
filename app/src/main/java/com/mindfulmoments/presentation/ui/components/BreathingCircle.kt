package com.mindfulmoments.presentation.ui.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import com.mindfulmoments.presentation.theme.InhaleColor
import com.mindfulmoments.presentation.theme.ExhaleColor
import com.mindfulmoments.presentation.theme.HoldColor

enum class BreathingPhase {
    INHALE, HOLD1, EXHALE, HOLD2
}

@Composable
fun BreathingCircle(
    phase: BreathingPhase,
    progress: Float,
    modifier: Modifier = Modifier
) {
    val animatedProgress by animateFloatAsState(
        targetValue = progress,
        animationSpec = tween(durationMillis = 300, easing = FastOutSlowInEasing),
        label = "breathing_progress"
    )

    val size by animateFloatAsState(
        targetValue = when (phase) {
            BreathingPhase.INHALE -> 0.8f + (animatedProgress * 0.2f)
            BreathingPhase.HOLD1 -> 1.0f
            BreathingPhase.EXHALE -> 1.0f - (animatedProgress * 0.2f)
            BreathingPhase.HOLD2 -> 0.8f
        },
        animationSpec = tween(durationMillis = 300, easing = FastOutSlowInEasing),
        label = "breathing_size"
    )

    val color = when (phase) {
        BreathingPhase.INHALE -> InhaleColor
        BreathingPhase.HOLD1 -> HoldColor
        BreathingPhase.EXHALE -> ExhaleColor
        BreathingPhase.HOLD2 -> HoldColor
    }

    val phaseText = when (phase) {
        BreathingPhase.INHALE -> "Вдох"
        BreathingPhase.HOLD1 -> "Задержка"
        BreathingPhase.EXHALE -> "Выдох"
        BreathingPhase.HOLD2 -> "Задержка"
    }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .aspectRatio(1f),
        contentAlignment = Alignment.Center
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val canvasSize = size.width
            val circleSize = canvasSize * size

            // Outer circle (stroke)
            drawCircle(
                brush = Brush.radialGradient(
                    colors = listOf(
                        color.copy(alpha = 0.3f),
                        color.copy(alpha = 0.1f)
                    )
                ),
                radius = circleSize / 2,
                style = Stroke(width = 4.dp.toPx())
            )

            // Inner circle (filled)
            drawCircle(
                brush = Brush.radialGradient(
                    colors = listOf(
                        color.copy(alpha = 0.4f),
                        color.copy(alpha = 0.2f),
                        Color.Transparent
                    )
                ),
                radius = circleSize / 2
            )
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = phaseText,
                style = MaterialTheme.typography.headlineMedium,
                color = color
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "${(progress * 100).toInt()}%",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}
