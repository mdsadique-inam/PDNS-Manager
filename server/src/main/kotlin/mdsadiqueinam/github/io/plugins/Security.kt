package mdsadiqueinam.github.io.plugins

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.interfaces.Payload
import io.ktor.http.*
import io.ktor.http.auth.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import mdsadiqueinam.github.io.extensions.jwtRsaConfig
import mdsadiqueinam.github.io.repositories.JwtRepository
import models.User
import org.koin.ktor.ext.inject

class JWTUserPrincipal(payload: Payload, val user: User) : Principal, JWTPayloadHolder(payload)

fun Application.configureSecurity() {
    authentication {
        jwt {
            val jwtConfig = this@configureSecurity.jwtRsaConfig
            realm = jwtConfig.realm
            verifier(
                JWT.require(Algorithm.RSA256(jwtConfig.publicKey, jwtConfig.privateKey)).withIssuer(jwtConfig.issuer)
                    .withAudience(jwtConfig.audience).build()
            )

            validate { credential ->
                val accessToken = this.request.authorization()
                val repository by inject<JwtRepository>()

                repository.validate(accessToken)?.let {
                    JWTUserPrincipal(credential.payload, it)
                }
            }

            challenge { _, _ ->
                call.respond(HttpStatusCode.Unauthorized, "Token is not valid or has expired")
            }
        }
    }
}
