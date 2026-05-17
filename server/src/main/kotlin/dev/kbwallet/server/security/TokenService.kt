package dev.kbwallet.server.security

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import java.util.Date

data class TokenConfig(
    val secret: String,
    val issuer: String,
    val audience: String,
    val accessTokenExpirationMs: Long,
    val refreshTokenExpirationMs: Long
)

class TokenService(private val config: TokenConfig) {

    fun generateAccessToken(userId: String, email: String): String {
        return JWT.create()
            .withIssuer(config.issuer)
            .withAudience(config.audience)
            .withSubject("access")
            .withClaim("userId", userId)
            .withClaim("email", email)
            .withIssuedAt(Date())
            .withExpiresAt(Date(System.currentTimeMillis() + config.accessTokenExpirationMs))
            .sign(Algorithm.HMAC256(config.secret))
    }

    fun generateRefreshToken(userId: String): String {
        return JWT.create()
            .withIssuer(config.issuer)
            .withAudience(config.audience)
            .withSubject("refresh")
            .withClaim("userId", userId)
            .withIssuedAt(Date())
            .withExpiresAt(Date(System.currentTimeMillis() + config.refreshTokenExpirationMs))
            .sign(Algorithm.HMAC256(config.secret))
    }

    fun validateAccessToken(token: String): String? {
        return try {
            val verifier = JWT.require(Algorithm.HMAC256(config.secret))
                .withIssuer(config.issuer)
                .withAudience(config.audience)
                .withSubject("access")
                .build()
            val decoded = verifier.verify(token)
            decoded.getClaim("userId").asString()
        } catch (e: Exception) {
            null
        }
    }

    fun validateRefreshToken(token: String): String? {
        return try {
            val verifier = JWT.require(Algorithm.HMAC256(config.secret))
                .withIssuer(config.issuer)
                .withAudience(config.audience)
                .withSubject("refresh")
                .build()
            val decoded = verifier.verify(token)
            decoded.getClaim("userId").asString()
        } catch (e: Exception) {
            null
        }
    }
}
