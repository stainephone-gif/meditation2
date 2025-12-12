package com.mindfulmoments.presentation.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.mindfulmoments.data.model.PracticeData
import com.mindfulmoments.domain.model.BreathingType
import com.mindfulmoments.presentation.ui.components.BreathingCircle
import com.mindfulmoments.presentation.ui.components.BreathingPhase
import kotlinx.coroutines.delay

@Composable
fun BreathingExerciseScreen(
    practiceId: String,
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    val practice = remember {
        PracticeData.getAllPractices().find { it.id == practiceId }
    }

    if (practice == null || practice.breathingType == null) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text("Практика не найдена")
        }
        return
    }

    val pattern = PracticeData.getBreathingPattern(practice.breathingType)
    var isRunning by remember { mutableStateOf(false) }
    var currentPhase by remember { mutableStateOf(BreathingPhase.INHALE) }
    var progress by remember { mutableStateOf(0f) }
    var cycleCount by remember { mutableStateOf(0) }

    LaunchedEffect(isRunning) {
        if (isRunning) {
            while (isRunning) {
                // Inhale
                currentPhase = BreathingPhase.INHALE
                for (i in 0..pattern.inhale * 10) {
                    progress = i / (pattern.inhale * 10f)
                    delay(100)
                }

                // Hold 1
                if (pattern.hold1 > 0) {
                    currentPhase = BreathingPhase.HOLD1
                    for (i in 0..pattern.hold1 * 10) {
                        progress = i / (pattern.hold1 * 10f)
                        delay(100)
                    }
                }

                // Exhale
                currentPhase = BreathingPhase.EXHALE
                for (i in 0..pattern.exhale * 10) {
                    progress = i / (pattern.exhale * 10f)
                    delay(100)
                }

                // Hold 2
                if (pattern.hold2 > 0) {
                    currentPhase = BreathingPhase.HOLD2
                    for (i in 0..pattern.hold2 * 10) {
                        progress = i / (pattern.hold2 * 10f)
                        delay(100)
                    }
                }

                cycleCount++
            }
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Header with back button
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onNavigateBack) {
                Icon(Icons.Default.ArrowBack, contentDescription = "Назад")
            }
            Text(
                text = practice.name,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.SemiBold
            )
            IconButton(onClick = { /* Settings */ }) {
                Icon(Icons.Default.Settings, contentDescription = "Настройки")
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        // Breathing Circle
        BreathingCircle(
            phase = currentPhase,
            progress = progress,
            modifier = Modifier
                .weight(1f)
                .padding(32.dp)
        )

        Spacer(modifier = Modifier.height(32.dp))

        // Stats
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant
            )
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "$cycleCount",
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Циклов",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                Divider(
                    modifier = Modifier
                        .height(48.dp)
                        .width(1.dp)
                )

                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    val totalSeconds = pattern.inhale + pattern.hold1 + pattern.exhale + pattern.hold2
                    Text(
                        text = "$totalSeconds сек",
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "На цикл",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Control Button
        Button(
            onClick = { isRunning = !isRunning },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            shape = RoundedCornerShape(16.dp)
        ) {
            Icon(
                imageVector = if (isRunning) Icons.Default.Pause else Icons.Default.PlayArrow,
                contentDescription = null
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = if (isRunning) "Пауза" else "Начать",
                style = MaterialTheme.typography.titleMedium
            )
        }

        Spacer(modifier = Modifier.height(16.dp))
    }
}
