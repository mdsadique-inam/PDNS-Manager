package mdsadiqueinam.github.io.util

import org.springframework.security.crypto.password.DelegatingPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder

fun createDelegatingPasswordEncoder(): PasswordEncoder {
	val encodingId = "argon2"
	val encoders: MutableMap<String, PasswordEncoder> = HashMap()
	encoders["bcrypt"] = org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder()
	encoders["pbkdf2"] = org.springframework.security.crypto.password.Pbkdf2PasswordEncoder.defaultsForSpringSecurity_v5_8()
	encoders["scrypt"] = org.springframework.security.crypto.scrypt.SCryptPasswordEncoder.defaultsForSpringSecurity_v5_8()
	encoders[encodingId] = org.springframework.security.crypto.argon2.Argon2PasswordEncoder.defaultsForSpringSecurity_v5_8()
	return DelegatingPasswordEncoder(encodingId, encoders)
}
