package services

import extensions.process
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.resources.post
import io.ktor.client.request.setBody
import models.ApiResponse
import models.LoginBody
import models.LoginResponse
import recources.Login

class AuthenticationService(private val client: HttpClient) {
    suspend fun login(body: LoginBody): ApiResponse<LoginResponse, String> {
        val response = client.post(Login()) {
            setBody(body)
        }
        return response.process<LoginResponse, String>()
    }

    suspend fun register(uid: String, password: String): Boolean {
        val res = login(LoginBody(uid, password))
        return when(res) {
            is ApiResponse.Error -> true
            is ApiResponse.Success -> true
        }
    }
}