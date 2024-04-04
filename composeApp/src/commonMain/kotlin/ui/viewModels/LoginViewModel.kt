package ui.viewModels

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class LoginViewModel : ViewModel() {
    private val _uid = mutableStateOf("")
    val uid: State<String> = _uid

    private val _password = mutableStateOf("")
    val password: State<String> = _password

    fun setUid(username: String) {
        _uid.value = username.trim()
    }

    fun setPassword(password: String) {
        _password.value = password.trim()
    }
}