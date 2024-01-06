package mdsadiqueinam.github.io.models

import java.security.KeyFactory
import java.security.interfaces.RSAPrivateKey
import java.security.interfaces.RSAPublicKey
import java.security.spec.X509EncodedKeySpec
import java.util.*

sealed class JWTConfig {
    abstract val issuer: String
    abstract val audience: String
    abstract val realm: String

    data class HMAC(
        override val issuer: String,
        override val audience: String,
        override val realm: String,
        val secretPem: String
    ) : JWTConfig()

    data class RSA(
        override val issuer: String,
        override val audience: String,
        override val realm: String,
        val publicKeyPem: String,
        val privateKeyPem: String
    ) : JWTConfig() {
        val publicKey: RSAPublicKey
            get() =
                KeyFactory.getInstance("RSA")
                    .generatePublic(X509EncodedKeySpec(Base64.getDecoder().decode(publicKeyPem))) as RSAPublicKey

        val privateKey: RSAPrivateKey
            get() = KeyFactory.getInstance("RSA")
                .generatePrivate(X509EncodedKeySpec(Base64.getDecoder().decode(privateKeyPem))) as RSAPrivateKey
    }
}
