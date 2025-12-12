package com.mindfulmoments.presentation.ui.navigation

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Practices : Screen("practices")
    object Timer : Screen("timer")
    object Progress : Screen("progress")
    object PracticeDetail : Screen("practice_detail/{practiceId}") {
        fun createRoute(practiceId: String) = "practice_detail/$practiceId"
    }
    object BreathingExercise : Screen("breathing_exercise/{practiceId}") {
        fun createRoute(practiceId: String) = "breathing_exercise/$practiceId"
    }
}
