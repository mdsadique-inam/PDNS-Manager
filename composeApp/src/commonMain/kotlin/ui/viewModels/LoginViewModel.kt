package ui.viewModels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import repositories.AuthenticationRepository

data class LoginScreenUiState(
    val uid: String = "",
    val password: String = "",
    val isLoading: Boolean = false,
    val error: String? = "Invalid Credentials"
) {
    val isError get() = error != null
}

class LoginViewModel(private val authenticationRepository: AuthenticationRepository) : ViewModel() {
    private val _uiState = MutableStateFlow(LoginScreenUiState())
    val uiState: StateFlow<LoginScreenUiState> = _uiState.asStateFlow()

    fun setUid(uid: String) {
        _uiState.value = _uiState.value.copy(uid = uid)
    }

    fun setPassword(password: String) {
        _uiState.value = _uiState.value.copy(password = password)
    }

    fun login() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            authenticationRepository.login(_uiState.value.uid, uiState.value.password)
        }
    }
}