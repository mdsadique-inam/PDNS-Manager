package services

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.resources.post
import io.ktor.client.request.setBody
import models.LoginBody
import models.LoginResponse
import recources.Login

class AuthenticationService(private val client: HttpClient) {
    suspend fun login(body: LoginBody) {
        val response = client.post(Login()) {
            setBody(body)
        }
        val result = response.body<LoginResponse>()
    }

    suspend fun register(uid: String, password: String): Boolean {
        return true
    }
}