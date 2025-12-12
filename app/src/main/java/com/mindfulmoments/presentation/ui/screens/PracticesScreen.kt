package com.mindfulmoments.presentation.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.mindfulmoments.data.model.PracticeData
import com.mindfulmoments.domain.model.PracticeType
import com.mindfulmoments.presentation.ui.components.PracticeCard

@Composable
fun PracticesScreen(
    onPracticeClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var selectedTab by remember { mutableStateOf(0) }
    val tabs = listOf("Дыхание", "Медитация", "Быстрые")

    Column(
        modifier = modifier.fillMaxSize()
    ) {
        // Header
        Text(
            text = "Практики",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(16.dp)
        )

        // Tabs
        TabRow(
            selectedTabIndex = selectedTab,
            containerColor = MaterialTheme.colorScheme.surface,
            contentColor = MaterialTheme.colorScheme.primary
        ) {
            tabs.forEachIndexed { index, title ->
                Tab(
                    selected = selectedTab == index,
                    onClick = { selectedTab = index },
                    text = { Text(title) }
                )
            }
        }

        // Content
        val practices = when (selectedTab) {
            0 -> PracticeData.breathingPractices
            1 -> PracticeData.meditationPractices
            2 -> PracticeData.quickTechniques
            else -> emptyList()
        }

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(practices) { practice ->
                PracticeCard(
                    practice = practice,
                    onClick = { onPracticeClick(practice.id) }
                )
            }

            item {
                Spacer(modifier = Modifier.height(80.dp))
            }
        }
    }
}
