package com.mindfulmoments.presentation.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomNavItem(
    val route: String,
    val icon: ImageVector,
    val label: String
) {
    object Home : BottomNavItem(
        route = Screen.Home.route,
        icon = Icons.Default.Home,
        label = "Главная"
    )

    object Practices : BottomNavItem(
        route = Screen.Practices.route,
        icon = Icons.Default.SelfImprovement,
        label = "Практики"
    )

    object Timer : BottomNavItem(
        route = Screen.Timer.route,
        icon = Icons.Default.Timer,
        label = "Таймер"
    )

    object Progress : BottomNavItem(
        route = Screen.Progress.route,
        icon = Icons.Default.ShowChart,
        label = "Прогресс"
    )
}

val bottomNavItems = listOf(
    BottomNavItem.Home,
    BottomNavItem.Practices,
    BottomNavItem.Timer,
    BottomNavItem.Progress
)
