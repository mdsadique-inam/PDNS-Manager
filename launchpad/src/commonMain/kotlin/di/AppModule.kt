package di

import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.resources.Resources
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.http.path
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.dsl.module
import repositories.repositoryModule
import services.servicesModule
import ui.viewModels.viewModelModule

object AppModule {
    val modules by lazy {
        listOf(module {
            single {
                HttpClient {
                    install(Resources)
                    install(ContentNegotiation) {
                        json(Json {
                            ignoreUnknownKeys = true
                            isLenient = true
                        })
                    }
                    defaultRequest {
                        url {
                            path("api/")
                        }
                        contentType(ContentType.Application.Json)
                    }
                }
            }
        }, servicesModule, repositoryModule, viewModelModule)
    }
}