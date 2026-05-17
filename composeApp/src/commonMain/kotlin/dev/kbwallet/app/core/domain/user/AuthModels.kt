package dev.kbwallet.app.core.domain.user

import kotlinx.serialization.Serializable

// ── Auth request DTOs ──

@Serializable
data class RegisterRequest(
    val email: String,
    val username: String,
    val password: String
)

@Serializable
data class LoginRequest(
    val email: String,
    val password: String
)

// ── Auth response DTOs ──

@Serializable
data class AuthResponse(
    val accessToken: String,
    val refreshToken: String,
    val user: User
)

// ── API error ──

@Serializable
data class ApiError(
    val error: String,
    val message: String
)
