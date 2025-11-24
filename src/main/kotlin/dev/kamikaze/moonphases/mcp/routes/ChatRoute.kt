package dev.kamikaze.moonphases.mcp.routes

import dev.kamikaze.moonphases.mcp.models.ChatRequest
import dev.kamikaze.moonphases.mcp.services.MoonChatProcessor
import io.ktor.http.*
import io.ktor.server.application.call
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.*
import io.ktor.server.routing.post
import kotlin.text.isBlank
import kotlin.to

fun Route.chatRoute(moonChatProcessor: MoonChatProcessor) {
    post("/chat") {
        val request = call.receive<ChatRequest>()

        if (request.message.isBlank()) {
            call.respond(HttpStatusCode.BadRequest, mapOf("error" to "Message cannot be empty"))
            return@post
        }

        val response = moonChatProcessor.processMessage(request.message)
        if (response != null) {
            call.respond(response)
        } else {
            call.respond(HttpStatusCode.BadRequest, mapOf("error" to "Could not process message"))
        }
    }
}