package repositories

import models.ApiResponse
import models.LoginBody
import models.LoginResponse
import models.RegisterBody
import models.ValidatedField
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

    suspend fun register(body: RegisterBody): Result<ApiResponse<LoginResponse, ValidatedField>> = kotlin.runCatching{
	    val response = authenticationService.register(body)
	    if (response is ApiResponse.Success) {
			redirectToApp()
	    }
	    return Result.success(response)
    }
}

expect fun AuthenticationRepository.redirectToApp()