package dev.kbwallet.server.plugins

import dev.kbwallet.server.routes.*
import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Application.configureRouting() {
    routing {
        healthRouting()
        authRouting()
        userRouting()
    }
}
