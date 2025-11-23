package dev.kamikaze.moonphases.mcp.plugins

import dev.kamikaze.moonphases.mcp.models.HealthResponse
import dev.kamikaze.moonphases.mcp.routes.moonPhaseRoute
import dev.kamikaze.moonphases.mcp.routes.toolsRoute
import dev.kamikaze.moonphases.mcp.services.MoonPhasesService
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureRouting() {
    val moonPhasesService = MoonPhasesService()

    routing {
        get("/") {
            call.respondText("Moon Phases MCP Server is running! 🌙")
        }

        get("/health") {
            call.respond(HealthResponse(
                status = "ok",
                service = "moon-phases-mcp",
                timestamp = System.currentTimeMillis()
            ))
        }

        toolsRoute()
        moonPhaseRoute(moonPhasesService)
    }
}
