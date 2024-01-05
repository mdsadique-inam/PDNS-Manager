package recources

import io.ktor.resources.*
import kotlinx.serialization.Serializable

@Resource("/greet")
class Greeting(val name: String? = "") {
    @Serializable
    data class Body(val name: String? = "")
}