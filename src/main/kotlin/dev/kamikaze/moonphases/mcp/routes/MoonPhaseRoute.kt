package dev.kamikaze.moonphases.mcp.routes

import dev.kamikaze.moonphases.mcp.models.ErrorResponse
import dev.kamikaze.moonphases.mcp.models.MoonPhaseRequest
import dev.kamikaze.moonphases.mcp.services.MoonPhasesService
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.moonPhaseRoute(moonPhasesService: MoonPhasesService) {
    
    // Получить фазу луны для конкретной даты
    post("/moonphase") {
        val request = call.receive<MoonPhaseRequest>()
        
        moonPhasesService.getMoonPhaseByDate(request.date)
            .onSuccess { response ->
                call.respond(response)
            }
            .onFailure { error ->
                call.respond(
                    HttpStatusCode.InternalServerError,
                    ErrorResponse(error = error.message ?: "Failed to fetch moon phase")
                )
            }
    }
    
    // Получить текущую фазу луны
    get("/moonphase/current") {
        moonPhasesService.getCurrentMoonPhase()
            .onSuccess { response ->
                call.respond(response)
            }
            .onFailure { error ->
                call.respond(
                    HttpStatusCode.InternalServerError,
                    ErrorResponse(error = error.message ?: "Failed to fetch current moon phase")
                )
            }
    }
    
    // Получить фазу луны через query параметр
    get("/moonphase") {
        val date = call.request.queryParameters["date"]
        
        if (date == null) {
            call.respond(
                HttpStatusCode.BadRequest,
                ErrorResponse(error = "Date parameter is required (format: MM-DD-YYYY)")
            )
            return@get
        }
        
        moonPhasesService.getMoonPhaseByDate(date)
            .onSuccess { response ->
                call.respond(response)
            }
            .onFailure { error ->
                call.respond(
                    HttpStatusCode.InternalServerError,
                    ErrorResponse(error = error.message ?: "Failed to fetch moon phase")
                )
            }
    }
}
