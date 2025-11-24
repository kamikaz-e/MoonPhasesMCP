package dev.kamikaze.moonphases.mcp.services

import dev.kamikaze.moonphases.mcp.models.MoonPhaseResponse

class MoonChatProcessor(
    private val moonPhasesService: MoonPhasesService
) {

    suspend fun processMessage(message: String): MoonPhaseResponse? {
        val lowerMessage = message.lowercase()

        return when {
            // Current moon phase (tool: current_moon_phase)
            lowerMessage.contains("current") ||
                    lowerMessage.contains("текущ") ||
                    lowerMessage.contains("сейчас") ||
                    lowerMessage.contains("what is") ||
                    lowerMessage.contains("какая") ||
                    lowerMessage.contains("today") ||
                    lowerMessage.contains("сегодня") -> {
                getCurrentMoonPhaseResponse()
            }

            // Moon phase by date (tool: moon_phase_by_date)
            lowerMessage.contains("on ") ||
                    lowerMessage.contains("was") ||
                    lowerMessage.contains("дат") ||
                    containsDate(message) -> {
                val date = extractDate(message)
                if (date != null) {
                    getMoonPhaseByDateResponse(date)
                } else {
                    null
                }
            }

            // Next full moon (tool: next_full_moon)
            lowerMessage.contains("next full moon") ||
                    lowerMessage.contains("следующее полнолуние") ||
                    lowerMessage.contains("when") && lowerMessage.contains("full moon") -> {
                getNextFullMoonResponse()
            }

            // Lunar information (tool: lunar_calendar)
            lowerMessage.contains("lunar information") ||
                    lowerMessage.contains("lunar") ||
                    lowerMessage.contains("лунн") ||
                    lowerMessage.contains("tell me about") ||
                    lowerMessage.contains("age") ||
                    lowerMessage.contains("distance") ||
                    lowerMessage.contains("lunation") -> {
                getLunarInformationResponse()
            }

            else -> {
                // Default: return current moon phase
                getCurrentMoonPhaseResponse()
            }
        }
    }

    private suspend fun getCurrentMoonPhaseResponse(): MoonPhaseResponse? {
        val moonPhaseResult = moonPhasesService.getCurrentMoonPhase()
        return moonPhaseResult.getOrNull()
    }

    private suspend fun getMoonPhaseByDateResponse(date: String): MoonPhaseResponse? {
        val moonPhaseResult = moonPhasesService.getMoonPhaseByDate(date)
        return moonPhaseResult.getOrNull()
    }

    private suspend fun getNextFullMoonResponse(): MoonPhaseResponse? {
        val moonPhaseResult = moonPhasesService.getCurrentMoonPhase()
        return moonPhaseResult.getOrNull()
    }

    private suspend fun getLunarInformationResponse(): MoonPhaseResponse? {
        val moonPhaseResult = moonPhasesService.getCurrentMoonPhase()
        return moonPhaseResult.getOrNull()
    }

    private fun containsDate(text: String): Boolean {
        val patterns = listOf(
            """\d{1,2}[-./]\d{1,2}[-./]\d{4}""",
            """\d{4}[-./]\d{1,2}[-./]\d{1,2}"""
        )
        return patterns.any { pattern ->
            text.contains(pattern.toRegex())
        }
    }

    private fun extractDate(text: String): String? {
        val patterns = listOf(
            """(\d{1,2})[-./](\d{1,2})[-./](\d{4})""".toRegex(),
            """(\d{4})[-./](\d{1,2})[-./](\d{1,2})""".toRegex()
        )

        for (pattern in patterns) {
            val match = pattern.find(text)
            if (match != null) {
                val groups = match.groupValues
                return when (groups.size) {
                    4 -> {
                        if (groups[1].length == 4) {
                            "${groups[2].padStart(2, '0')}-${groups[3].padStart(2, '0')}-${groups[1]}"
                        } else {
                            "${groups[1].padStart(2, '0')}-${groups[2].padStart(2, '0')}-${groups[3]}"
                        }
                    }
                    else -> null
                }
            }
        }
        return null
    }
}