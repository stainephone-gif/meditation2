package com.mindfulmoments.data.model

import com.mindfulmoments.domain.model.*

object PracticeData {

    val breathingPractices = listOf(
        Practice(
            id = "breathing_478",
            name = "4-7-8 дыхание",
            description = "Вдох 4 сек, задержка 7 сек, выдох 8 сек. Идеально для быстрого успокоения.",
            type = PracticeType.BREATHING,
            durationMinutes = 5,
            icon = "🫁",
            breathingType = BreathingType.BREATHING_478
        ),
        Practice(
            id = "box_breathing",
            name = "Box дыхание",
            description = "Вдох-задержка-выдох-задержка по 4 секунды. Техника морских котиков.",
            type = PracticeType.BREATHING,
            durationMinutes = 5,
            icon = "📦",
            breathingType = BreathingType.BOX_BREATHING
        ),
        Practice(
            id = "deep_breathing",
            name = "Глубокое дыхание",
            description = "Медленное диафрагмальное дыхание для расслабления.",
            type = PracticeType.BREATHING,
            durationMinutes = 10,
            icon = "🌊",
            breathingType = BreathingType.DEEP_BREATHING
        ),
        Practice(
            id = "energizing",
            name = "Энергизирующее дыхание",
            description = "Быстрое дыхание для повышения энергии и концентрации.",
            type = PracticeType.BREATHING,
            durationMinutes = 3,
            icon = "⚡",
            breathingType = BreathingType.ENERGIZING
        )
    )

    val meditationPractices = listOf(
        Practice(
            id = "body_scan",
            name = "Body Scan",
            description = "Сканирование тела от головы до ног. Осознание физических ощущений.",
            type = PracticeType.MEDITATION,
            durationMinutes = 15,
            icon = "🧘"
        ),
        Practice(
            id = "mindfulness",
            name = "Осознанность",
            description = "Фокус на настоящем моменте. Наблюдение без оценки.",
            type = PracticeType.MEDITATION,
            durationMinutes = 10,
            icon = "🌸"
        ),
        Practice(
            id = "visualization",
            name = "Визуализация",
            description = "Успокаивающие образы природы: океан, лес, горы.",
            type = PracticeType.MEDITATION,
            durationMinutes = 12,
            icon = "🏞️"
        ),
        Practice(
            id = "loving_kindness",
            name = "Любящая доброта",
            description = "Медитация сострадания к себе и другим.",
            type = PracticeType.MEDITATION,
            durationMinutes = 10,
            icon = "💚"
        )
    )

    val quickTechniques = listOf(
        Practice(
            id = "54321",
            name = "5-4-3-2-1",
            description = "Техника заземления: 5 вещей, которые видишь, 4 - слышишь, 3 - чувствуешь, 2 - пахнут, 1 - ощущаешь на вкус.",
            type = PracticeType.QUICK_TECHNIQUE,
            durationMinutes = 3,
            icon = "🖐️"
        ),
        Practice(
            id = "muscle_relax",
            name = "Мышечная релаксация",
            description = "Последовательное напряжение и расслабление групп мышц.",
            type = PracticeType.QUICK_TECHNIQUE,
            durationMinutes = 5,
            icon = "💪"
        ),
        Practice(
            id = "thought_cloud",
            name = "Наблюдение за мыслями",
            description = "Представляйте мысли как облака, плывущие по небу.",
            type = PracticeType.QUICK_TECHNIQUE,
            durationMinutes = 3,
            icon = "☁️"
        )
    )

    fun getAllPractices() = breathingPractices + meditationPractices + quickTechniques

    fun getBreathingPattern(type: BreathingType): BreathingPattern {
        return when (type) {
            BreathingType.BREATHING_478 -> BreathingPattern(4, 7, 8, 0)
            BreathingType.BOX_BREATHING -> BreathingPattern(4, 4, 4, 4)
            BreathingType.DEEP_BREATHING -> BreathingPattern(5, 0, 7, 0)
            BreathingType.ENERGIZING -> BreathingPattern(2, 0, 2, 0)
        }
    }
}
