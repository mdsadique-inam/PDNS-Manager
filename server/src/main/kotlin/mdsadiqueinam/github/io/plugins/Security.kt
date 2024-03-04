package mdsadiqueinam.github.io.plugins

import com.auth0.jwt.interfaces.Payload
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.response.*
import mdsadiqueinam.github.io.repositories.AuthenticationRepository
import mdsadiqueinam.github.io.repositories.UserRepository
import models.User
import org.koin.ktor.ext.inject

class JWTUserPrincipal(payload: Payload, val user: User) : Principal, JWTPayloadHolder(payload)

fun Application.configureSecurity() {
    authentication {
        val authenticationRepository by this@configureSecurity.inject<AuthenticationRepository>()
        val userRepository by this@configureSecurity.inject<UserRepository>()
        jwt {
            realm = authenticationRepository.realm
            verifier(
                authenticationRepository.getJwtVerifier()
            )

            validate { credential ->
                val id = credential.payload.getClaim("id").asString()
                userRepository.findOrNull(id)?.let {
                    JWTUserPrincipal(credential.payload, it)
                }
            }

            challenge { _, _ ->
                call.respond(HttpStatusCode.Unauthorized, "Token is not valid or has expired")
            }
        }
    }
}
