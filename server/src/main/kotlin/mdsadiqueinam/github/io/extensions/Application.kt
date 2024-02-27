package mdsadiqueinam.github.io.extensions

import io.ktor.server.application.*
import mdsadiqueinam.github.io.models.JWTConfig

private lateinit var jwtConfiguration: JWTConfig

fun createJWTConfig(environment: ApplicationEnvironment) {
    val _jwtConfig = environment.config.config("jwt")
    _jwtConfig.propertyOrNull("secret")?.let {
        if (it.getString().isEmpty()) throw Exception("JWT secret token is missing in application.conf file")
        jwtConfiguration = JWTConfig.HMAC(
            issuer = _jwtConfig.property("issuer").getString(),
            audience = _jwtConfig.property("audience").getString(),
            realm = _jwtConfig.property("realm").getString(),
            secretPem = it.getString()
        )
        return
    }

    _jwtConfig.propertyOrNull("publicKey")?.let { publicKey ->
        if (publicKey.getString().isEmpty()) throw Exception("JWT public key is missing in application.conf file")
        _jwtConfig.propertyOrNull("privateKey")?.let { privateKey ->
            if (privateKey.getString().isEmpty()) {
                throw Exception(
                    "JWT private key is missing in application.conf file"
                )
            }

            jwtConfiguration = JWTConfig.RSA(
                issuer = _jwtConfig.property("issuer").getString(),
                audience = _jwtConfig.property("audience").getString(),
                realm = _jwtConfig.property("realm").getString(),
                publicKeyPem = publicKey.getString().replace("\\n".toRegex(), "\n").trimIndent()
                    .replace("-----BEGIN PUBLIC KEY-----", "").replace("-----END PUBLIC KEY-----", "")
                    .replace("\\s".toRegex(), ""),
                privateKeyPem = privateKey.getString().replace("\\n".toRegex(), "\n").trimIndent()
                    .replace("-----BEGIN PRIVATE KEY-----", "").replace("-----END PRIVATE KEY-----", "")
                    .replace("\\s".toRegex(), "")
            )
            return
        }
    }
    throw Exception("JWT secret or RSA keys are missing in application.conf file")
}

// getter on Application that will return the JWTConfig object from the application.conf file.
@Suppress("TooGenericExceptionThrown")
val Application.jwtConfig: JWTConfig
    get() {
        if (!::jwtConfiguration.isInitialized) {
            createJWTConfig(this.environment)
        }
        return jwtConfiguration
    }

@Suppress("TooGenericExceptionThrown")
val Application.jwtRsaConfig: JWTConfig.RSA
    get() {
        return when (jwtConfig) {
            is JWTConfig.RSA -> jwtConfig as JWTConfig.RSA
            else -> throw Exception("JWT RSA keys are missing in application.conf file")
        }
    }

@Suppress("TooGenericExceptionThrown")
val Application.jwtHmacConfig: JWTConfig.HMAC
    get() {
        return when (jwtConfig) {
            is JWTConfig.HMAC -> jwtConfig as JWTConfig.HMAC
            else -> throw Exception("JWT secret keys are missing in application.conf file")
        }
    }

val Application.key: String
    get() {
        return this.environment.config.config("ktor.application").property("key").getString()
    }
