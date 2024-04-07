package repositories

import models.ApiResponse
import models.LoginBody
import services.AuthenticationService

class AuthenticationRepository(private val authenticationService: AuthenticationService) {
    suspend fun login(uid: String, password: String): Result<Unit> = kotlin.runCatching {
        return when (val response = authenticationService.login(LoginBody(uid, password))) {
            is ApiResponse.Success -> {
                redirectToApp()
                Result.success(Unit)
            }
            is ApiResponse.Error -> {
                Result.failure(Throwable(response.message))
            }
        }
    }
}

expect fun AuthenticationRepository.redirectToApp()