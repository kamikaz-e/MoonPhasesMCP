package dev.kamikaze.moonphases.mcp.models

import kotlinx.serialization.Serializable

@Serializable
data class MoonPhaseResponse(
    val phase: String,
    val phaseEmoji: String,
    val waxing: Boolean,
    val waning: Boolean,
    val lunarAge: Double,
    val lunarAgePercent: Double,
    val lunationNumber: Int,
    val lunarDistance: Double,
    val nextFullMoon: String,
    val lastFullMoon: String
)

@Serializable
data class ApiVerveResponse(
    val status: String,
    val error: String?,
    val data: MoonPhaseData?
)

@Serializable
data class MoonPhaseData(
    val phase: String,
    val phaseEmoji: String,
    val waxing: Boolean,
    val waning: Boolean,
    val lunarAge: Double,
    val lunarAgePercent: Double,
    val lunationNumber: Int,
    val lunarDistance: Double,
    val nextFullMoon: String,
    val lastFullMoon: String
)

@Serializable
data class ToolsResponse(
    val tools: List<ToolItem>
)

@Serializable
data class ToolItem(
    val id: String,
    val title: String,
    val description: String,
    val sampleQuery: String
)

@Serializable
data class HealthResponse(
    val status: String,
    val service: String,
    val timestamp: Long
)

@Serializable
data class ErrorResponse(
    val error: String,
    val type: String? = null
)
