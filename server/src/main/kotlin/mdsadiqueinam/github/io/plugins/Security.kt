package mdsadiqueinam.github.io.plugins

import com.auth0.jwt.interfaces.Payload
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.auth.Principal
import io.ktor.server.auth.authentication
import io.ktor.server.auth.jwt.JWTPayloadHolder
import io.ktor.server.auth.jwt.jwt
import io.ktor.server.auth.session
import io.ktor.server.response.respond
import io.ktor.server.sessions.SessionTransportTransformerEncrypt
import io.ktor.server.sessions.Sessions
import io.ktor.server.sessions.cookie
import io.ktor.util.hex
import mdsadiqueinam.github.io.repositories.AuthenticationRepository
import mdsadiqueinam.github.io.repositories.UserRepository
import models.User
import org.koin.ktor.ext.inject

object AuthenticationType {
    const val JWT = "JWT_AUTH"
    const val SESSION = "SESSION_AUTH"
}

class UserPrincipal(payload: Payload, val user: User) : Principal, JWTPayloadHolder(payload)
data class UserSession(val token: String) : Principal {
    companion object {
        val name = "__User-Session"
    }
}

fun Application.configureSecurity() {
    install(Sessions) {
        val secretEncryptKey = hex("00112233445566778899aabbccddeeff")
        val secretSignKey = hex("6819b57a326945c1968f45236589")
        cookie<UserSession>(UserSession.name) {
            cookie.path = "/"
            cookie.maxAgeInSeconds = 60 * 60 * 24
            cookie.httpOnly = true
            cookie.secure = true
            cookie.extensions.apply {
                set("SameSite", "None")
                set("Partitioned", "true")
            }
            transform(SessionTransportTransformerEncrypt(secretEncryptKey, secretSignKey))
        }
    }
    authentication {
        val authenticationRepository by this@configureSecurity.inject<AuthenticationRepository>()
        val userRepository by this@configureSecurity.inject<UserRepository>()
        jwt(AuthenticationType.JWT) {
            realm = authenticationRepository.realm
            verifier(
                authenticationRepository.getJwtVerifier()
            )

            validate { credential ->
                val id = credential.payload.getClaim("id").asString()
                userRepository.findOrNull(id)?.let {
                    UserPrincipal(credential.payload, it)
                }
            }

            challenge { _, _ ->
                call.respond(HttpStatusCode.Unauthorized, "Token is not valid or has expired")
            }
        }
        session<UserSession>(AuthenticationType.SESSION) {
            validate {
                val verifier = authenticationRepository.getJwtVerifier()
                verifier.verify(it.token)?.let { payload ->
                    val id = payload.getClaim("id").asString()
                    userRepository.findOrNull(id)?.let { user ->
                        UserPrincipal(payload, user)
                    }
                }
            }
            challenge {
                call.respond(HttpStatusCode.Unauthorized, "Session is not valid or has expired")
            }
        }
    }
}
