package extensions

import io.ktor.client.call.body
import io.ktor.client.statement.HttpResponse
import models.ApiResponse


suspend inline fun <reified S, reified E> HttpResponse.process(): ApiResponse<S, E> {
    return when (status.value) {
        in 200..299 -> this.body<ApiResponse.Success<S>>()
        else -> this.body<ApiResponse.Error<E>>()
    }
}