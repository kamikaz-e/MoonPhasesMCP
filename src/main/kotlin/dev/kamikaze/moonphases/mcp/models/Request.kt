package dev.kamikaze.moonphases.mcp.models

import kotlinx.serialization.Serializable

@Serializable
data class MoonPhaseRequest(
    val date: String // Format: MM-DD-YYYY
)
