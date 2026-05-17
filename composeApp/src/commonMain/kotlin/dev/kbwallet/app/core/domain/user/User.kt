package dev.kbwallet.app.core.domain.user

import kotlinx.serialization.Serializable

@Serializable
data class User(
    val id: String,
    val email: String,
    val username: String,
    val avatarUrl: String? = null,
    val bio: String? = null,
    val createdAt: Long
)
