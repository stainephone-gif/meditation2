package com.mindfulmoments.presentation.ui.screens

import androidx.compose.animation.core.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimerScreen(
    modifier: Modifier = Modifier
) {
    var selectedDuration by remember { mutableStateOf(10) }
    var isRunning by remember { mutableStateOf(false) }
    var remainingTime by remember { mutableStateOf(selectedDuration * 60) }

    LaunchedEffect(isRunning) {
        while (isRunning && remainingTime > 0) {
            delay(1000)
            remainingTime--
        }
        if (remainingTime == 0) {
            isRunning = false
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        // Header
        Text(
            text = "Таймер медитации",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        )

        Spacer(modifier = Modifier.height(32.dp))

        // Timer Circle
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(280.dp)
                .padding(16.dp)
        ) {
            val progress = if (isRunning) {
                1f - (remainingTime.toFloat() / (selectedDuration * 60))
            } else {
                0f
            }

            CircularProgressIndicator(
                progress = progress,
                modifier = Modifier.fillMaxSize(),
                strokeWidth = 12.dp,
                trackColor = MaterialTheme.colorScheme.surfaceVariant,
                strokeCap = StrokeCap.Round,
                color = MaterialTheme.colorScheme.primary
            )

            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                val minutes = remainingTime / 60
                val seconds = remainingTime % 60
                Text(
                    text = String.format("%02d:%02d", minutes, seconds),
                    style = MaterialTheme.typography.displayLarge,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = if (isRunning) "Медитация" else "Готов к началу",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        // Duration Selection
        if (!isRunning) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant
                )
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        text = "Длительность",
                        style = MaterialTheme.typography.titleMedium
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        listOf(5, 10, 15, 20).forEach { duration ->
                            FilterChip(
                                selected = selectedDuration == duration,
                                onClick = {
                                    selectedDuration = duration
                                    remainingTime = duration * 60
                                },
                                label = { Text("$duration мин") }
                            )
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        // Control Buttons
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            if (isRunning) {
                FilledTonalButton(
                    onClick = {
                        isRunning = false
                        remainingTime = selectedDuration * 60
                    },
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(12.dp),
                    contentPadding = PaddingValues(16.dp)
                ) {
                    Icon(Icons.Default.Stop, contentDescription = null)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Остановить")
                }

                Button(
                    onClick = { isRunning = !isRunning },
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(12.dp),
                    contentPadding = PaddingValues(16.dp)
                ) {
                    Icon(Icons.Default.Pause, contentDescription = null)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Пауза")
                }
            } else {
                Button(
                    onClick = { isRunning = true },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    contentPadding = PaddingValues(16.dp)
                ) {
                    Icon(Icons.Default.PlayArrow, contentDescription = null)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Начать", style = MaterialTheme.typography.titleMedium)
                }
            }
        }

        Spacer(modifier = Modifier.height(80.dp))
    }
}
