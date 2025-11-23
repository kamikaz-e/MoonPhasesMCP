package dev.kamikaze.moonphases.mcp.routes

import dev.kamikaze.moonphases.mcp.models.ToolItem
import dev.kamikaze.moonphases.mcp.models.ToolsResponse
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.toolsRoute() {
    get("/tools") {
        val tools = listOf(
            ToolItem(
                id = "current_moon_phase",
                title = "Current Moon Phase",
                description = "Get the current moon phase information",
                sampleQuery = "What is the current moon phase?"
            ),
            ToolItem(
                id = "moon_phase_by_date",
                title = "Moon Phase by Date",
                description = "Get moon phase information for a specific date",
                sampleQuery = "What was the moon phase on 11-19-2025?"
            ),
            ToolItem(
                id = "next_full_moon",
                title = "Next Full Moon",
                description = "Find out when the next full moon will occur",
                sampleQuery = "When is the next full moon?"
            ),
            ToolItem(
                id = "lunar_calendar",
                title = "Lunar Information",
                description = "Get detailed lunar information including age, distance, and lunation",
                sampleQuery = "Tell me about today's lunar information"
            )
        )
        
        call.respond(ToolsResponse(tools = tools))
    }
}
