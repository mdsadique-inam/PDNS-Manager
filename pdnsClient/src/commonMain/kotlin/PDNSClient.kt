import io.ktor.client.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.client.plugins.resources.*
import io.ktor.serialization.kotlinx.json.*

object PDNSClient {
    fun createHttpClient(block: HttpClientConfig<*>.() -> Unit): HttpClient {
        return HttpClient {
            install(Resources)
            install(ContentNegotiation) {
                json()
            }
            install(Logging) {
//                logger = object: Logger {
//                    override fun log(message: String) {
//                        Napier.v("HTTP Client", null, message)
//                    }
//                }
//                level = LogLevel.HEADERS
            }
            apply(block)
        }
    }
}