package repositories

import models.LoginBody
import services.AuthenticationService

class AuthenticationRepository(private val authenticationService: AuthenticationService) {
    suspend fun login(uid: String, password: String) {
        authenticationService.login(LoginBody(uid, password))
        this.redirect()
    }
}

expect fun AuthenticationRepository.redirect()