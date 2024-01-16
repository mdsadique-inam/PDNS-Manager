import io.ktor.client.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.resources.*
import io.ktor.serialization.kotlinx.json.*

object PDNSClient {
    fun createHttpClient(): HttpClient {
        return HttpClient() {
            install(Resources)
            install(ContentNegotiation) {
                json()
            }
        }
    }
}