package dev.kbwallet.server.routes

import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.healthRouting() {
    route("/api") {
        get("/health") {
            call.respond(mapOf("status" to "ok", "service" to "KB Wallet API", "version" to "1.0.0"))
        }
        get("/") {
            call.respond(mapOf("message" to "KB Wallet API Server"))
        }
    }
}
