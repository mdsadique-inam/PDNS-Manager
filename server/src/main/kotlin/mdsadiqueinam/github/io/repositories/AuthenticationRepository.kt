package mdsadiqueinam.github.io.repositories

import com.auth0.jwt.JWT
import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.algorithms.Algorithm
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import mdsadiqueinam.github.io.database.services.UserService
import mdsadiqueinam.github.io.exceptions.InvalidCredentialException
import mdsadiqueinam.github.io.models.JWTConfig
import models.LoginResponse
import models.RegisterBody
import models.User
import org.jetbrains.exposed.sql.transactions.transaction
import org.koin.core.annotation.Single
import org.springframework.security.crypto.password.PasswordEncoder
import java.util.*

@Single
class AuthenticationRepository(
    private val jwtConfig: JWTConfig,
    private val userService: UserService,
    private val encoder: PasswordEncoder
) {
    val realm get() = jwtConfig.realm

    private fun getAlgorithm(): Algorithm {
        return when (jwtConfig) {
            is JWTConfig.HMAC -> Algorithm.HMAC256(jwtConfig.secretPem)
            is JWTConfig.RSA -> Algorithm.RSA256(jwtConfig.publicKey, jwtConfig.privateKey)
        }
    }

    private fun getJwtRSAVerifier(config: JWTConfig.RSA): JWTVerifier {
        return JWT.require(getAlgorithm()).withIssuer(config.issuer)
            .withAudience(config.audience).build()
    }

    fun getJwtVerifier(): JWTVerifier {
        return when (jwtConfig) {
            is JWTConfig.HMAC -> TODO("Not implemented yet.")
            is JWTConfig.RSA -> getJwtRSAVerifier(jwtConfig)
        }
    }

    private fun generateRSAToken(user: User, expiresMultiple: Int = 1): String {
        return JWT.create().withSubject(jwtConfig.subject).withIssuer(jwtConfig.issuer)
            .withClaim(user::id.name, user.id).withClaim(user::username.name, user.username)
            .withExpiresAt(Date((System.currentTimeMillis() + jwtConfig.expires) * expiresMultiple))
            .withAudience(jwtConfig.audience).sign(getAlgorithm())
    }

    private fun generateToken(user: User): String {
        return when (jwtConfig) {
            is JWTConfig.HMAC -> TODO("Not implemented yet.")
            is JWTConfig.RSA -> generateRSAToken(user)
        }
    }

    private fun generateRefreshToken(user: User): String {
        return when (jwtConfig) {
            is JWTConfig.HMAC -> TODO("Not implemented yet.")
            is JWTConfig.RSA -> generateRSAToken(user, 7)
        }
    }

    fun attempt(uid: String, password: String): LoginResponse {
        return transaction {
            val userEntity = userService.findByUsernameOrEmail(uid)
            if (userEntity == null) {
                /**
                 * If the user is not found, we hash the password to prevent timing attacks
                 * and throw an exception
                 */
                encoder.encode(password)
                throw InvalidCredentialException()
            }

            if (!encoder.matches(password, userEntity.password)) {
                throw InvalidCredentialException()
            }

            login(userEntity.toModel())
        }
    }

    fun register(body: RegisterBody): LoginResponse {
        return transaction {
            val userEntity = userService.create(
                User(
                    id = "",
                    name = body.name,
                    username = body.username,
                    email = body.email,
                    createdAt = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()),
                ),
                encoder.encode(body.password)
            )
            login(userEntity.toModel())
        }
    }

    fun login(user: User): LoginResponse {
        val token = generateToken(user)
        val refreshToken = generateRefreshToken(user)
        return LoginResponse(token, refreshToken, user)
    }

}