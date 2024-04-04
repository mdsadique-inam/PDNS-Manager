package extensions

import exceptions.PDNSApiException
import io.ktor.client.call.body
import io.ktor.client.statement.HttpResponse
import io.ktor.http.HttpStatusCode
import models.ApiResponse
import models.Error


suspend inline fun <reified S, reified E> HttpResponse.process(): ApiResponse {
    return when (status.value) {
        in 200..299 -> this.body<ApiResponse.Success<S>>()
        else -> this.body<ApiResponse.Error<E>>()
    }
}