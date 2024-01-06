package mdsadiqueinam.github.io.extensions

import mdsadiqueinam.github.io.models.JWTConfig
import io.ktor.server.application.Application

// getter on Application that will return the JWTConfig object from the application.conf file.
@Suppress("TooGenericExceptionThrown")
val Application.jwtConfig: JWTConfig
    get() {
        val jwtConfig = environment.config.config("jwt")
        jwtConfig.propertyOrNull("secret")?.let {
            if (it.getString().isEmpty()) throw Exception("JWT secret token is missing in application.conf file")
            return JWTConfig.HMAC(
                issuer = jwtConfig.property("issuer").getString(),
                audience = jwtConfig.property("audience").getString(),
                realm = jwtConfig.property("realm").getString(),
                secretPem = it.getString()
            )
        }

        jwtConfig.propertyOrNull("publicKey")?.let { publicKey ->
            if (publicKey.getString().isEmpty()) throw Exception("JWT public key is missing in application.conf file")
            jwtConfig.propertyOrNull("privateKey")?.let { privateKey ->
                if (privateKey.getString().isEmpty()) {
                    throw Exception(
                        "JWT private key is missing in application.conf file"
                    )
                }

                return JWTConfig.RSA(
                    issuer = jwtConfig.property("issuer").getString(),
                    audience = jwtConfig.property("audience").getString(),
                    realm = jwtConfig.property("realm").getString(),
                    publicKeyPem = publicKey.getString().replace("\\n".toRegex(), "\n").trimIndent()
                        .replace("-----BEGIN PUBLIC KEY-----", "").replace("-----END PUBLIC KEY-----", "")
                        .replace("\\s".toRegex(), ""),
                    privateKeyPem = privateKey.getString().replace("\\n".toRegex(), "\n").trimIndent()
                        .replace("-----BEGIN PRIVATE KEY-----", "").replace("-----END PRIVATE KEY-----", "")
                        .replace("\\s".toRegex(), "")
                )
            }
        }
        throw Exception("JWT secret or RSA keys are missing in application.conf file")
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
