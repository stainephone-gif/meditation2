package com.mindfulmoments.domain.model

enum class PracticeType {
    BREATHING,
    MEDITATION,
    QUICK_TECHNIQUE
}

enum class BreathingType {
    BREATHING_478,
    BOX_BREATHING,
    DEEP_BREATHING,
    ENERGIZING
}

data class Practice(
    val id: String,
    val name: String,
    val description: String,
    val type: PracticeType,
    val durationMinutes: Int,
    val icon: String,
    val breathingType: BreathingType? = null
)

data class BreathingPattern(
    val inhale: Int,
    val hold1: Int,
    val exhale: Int,
    val hold2: Int
)

data class MeditationSession(
    val id: String,
    val practiceId: String,
    val startTime: Long,
    val endTime: Long,
    val durationSeconds: Int,
    val completed: Boolean
)
