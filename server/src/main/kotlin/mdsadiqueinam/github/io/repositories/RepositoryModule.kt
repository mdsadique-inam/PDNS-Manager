package mdsadiqueinam.github.io.repositories

import io.ktor.client.plugins.*
import io.ktor.client.request.*
import io.ktor.http.*
import mdsadiqueinam.github.io.database.services.UserService
import mdsadiqueinam.github.io.models.JWTConfig
import org.koin.core.annotation.Module
import org.koin.core.annotation.Single
import org.koin.dsl.module
import org.koin.ksp.generated.module
import org.springframework.security.crypto.password.PasswordEncoder
import services.ZoneService


val pdnsClientModule = module {
    val client = PDNSClient.createHttpClient {
        defaultRequest {
            url("http://localhost:8081/api/v1/")
            header("X-API-Key", "secret")
            contentType(ContentType.Application.Json)
        }
    }
    single { ZoneService(client) }
}

@Module()
class RepositoryModule(private val jwtConfig: JWTConfig) {

    init {
        this.module.includes(pdnsClientModule)
    }

    @Suppress("unused")
    @Single
    fun authenticationRepository(userService: UserService, encoder: PasswordEncoder) = AuthenticationRepository(jwtConfig, userService, encoder)
}