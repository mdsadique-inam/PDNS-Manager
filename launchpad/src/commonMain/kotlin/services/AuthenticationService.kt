package services

import extensions.process
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.resources.post
import io.ktor.client.request.setBody
import models.ApiResponse
import models.LoginBody
import models.LoginResponse
import models.RegisterBody
import models.ValidatedField
import recources.Login
import recources.Register

class AuthenticationService(private val client: HttpClient) {
    suspend fun login(body: LoginBody): ApiResponse<LoginResponse, String> {
        val response = client.post(Login()) {
            setBody(body)
        }
        return response.process<LoginResponse, String>()
    }

    suspend fun register(body: RegisterBody): ApiResponse<LoginResponse, ValidatedField> {
        val response = client.post(Register()) {
            setBody(body)
        }
        return response.process<LoginResponse, ValidatedField>()
    }
}