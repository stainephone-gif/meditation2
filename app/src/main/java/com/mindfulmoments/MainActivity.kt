package com.mindfulmoments

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.mindfulmoments.presentation.theme.MindfulMomentsTheme
import com.mindfulmoments.presentation.ui.navigation.BottomNavItem
import com.mindfulmoments.presentation.ui.navigation.Screen
import com.mindfulmoments.presentation.ui.navigation.bottomNavItems
import com.mindfulmoments.presentation.ui.screens.*

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MindfulMomentsTheme {
                MindfulMomentsApp()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MindfulMomentsApp() {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    // Determine if bottom bar should be visible
    val showBottomBar = when (currentRoute) {
        Screen.Home.route,
        Screen.Practices.route,
        Screen.Timer.route,
        Screen.Progress.route -> true
        else -> false
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            if (showBottomBar) {
                NavigationBar {
                    bottomNavItems.forEach { item ->
                        NavigationBarItem(
                            selected = currentRoute == item.route,
                            onClick = {
                                navController.navigate(item.route) {
                                    popUpTo(Screen.Home.route) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            },
                            icon = {
                                Icon(
                                    imageVector = item.icon,
                                    contentDescription = item.label
                                )
                            },
                            label = { Text(item.label) }
                        )
                    }
                }
            }
        }
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = Screen.Home.route,
            modifier = Modifier.padding(paddingValues)
        ) {
            composable(Screen.Home.route) {
                HomeScreen(
                    onPracticeClick = { practiceId ->
                        navController.navigate(Screen.BreathingExercise.createRoute(practiceId))
                    }
                )
            }

            composable(Screen.Practices.route) {
                PracticesScreen(
                    onPracticeClick = { practiceId ->
                        navController.navigate(Screen.BreathingExercise.createRoute(practiceId))
                    }
                )
            }

            composable(Screen.Timer.route) {
                TimerScreen()
            }

            composable(Screen.Progress.route) {
                ProgressScreen()
            }

            composable(Screen.BreathingExercise.route) { backStackEntry ->
                val practiceId = backStackEntry.arguments?.getString("practiceId") ?: ""
                BreathingExerciseScreen(
                    practiceId = practiceId,
                    onNavigateBack = { navController.navigateUp() }
                )
            }
        }
    }
}
