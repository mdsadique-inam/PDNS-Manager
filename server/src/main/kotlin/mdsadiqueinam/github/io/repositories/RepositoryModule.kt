package mdsadiqueinam.github.io.repositories

import mdsadiqueinam.github.io.database.services.UserService
import mdsadiqueinam.github.io.models.JWTConfig
import org.koin.core.annotation.Module
import org.koin.core.annotation.Single
import org.springframework.security.crypto.password.PasswordEncoder

@Module
class RepositoryModule(private val jwtConfig: JWTConfig) {

    @Single
    fun authenticationRepository(userService: UserService, encoder: PasswordEncoder) = AuthenticationRepository(jwtConfig, userService, encoder)
}