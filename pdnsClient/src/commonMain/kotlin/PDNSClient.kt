import io.ktor.client.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.client.plugins.resources.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json

object PDNSClient {
    fun createHttpClient(block: HttpClientConfig<*>.() -> Unit): HttpClient {
        return HttpClient {
            install(Resources)
            install(ContentNegotiation) {
                json(Json {
                    ignoreUnknownKeys = true
                    isLenient = true
                })
            }
            install(Logging) {
//                logger = object: Logger {
//                    override fun log(message: String) {
//                        Napier.v("HTTP Client", null, message)
//                    }
//                }
//                level = LogLevel.HEADERS
            }
            block()
        }
    }
}