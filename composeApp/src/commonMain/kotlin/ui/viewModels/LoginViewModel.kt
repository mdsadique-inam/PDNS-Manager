package ui.viewModels

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import repositories.AuthenticationRepository

class LoginViewModel(private val authenticationRepository: AuthenticationRepository) : ViewModel() {
    private val _uid = mutableStateOf("")
    val uid: State<String> = _uid

    private val _password = mutableStateOf("")
    val password: State<String> = _password

    fun setUid(uid: String) {
        _uid.value = uid.trim()
    }

    fun setPassword(password: String) {
        _password.value = password
    }
}