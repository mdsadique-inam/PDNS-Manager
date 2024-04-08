package ui.viewModels

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
	val error: String? = null
) {
	val isError get() = error != null
}

class LoginViewModel(private val authenticationRepository: AuthenticationRepository) : ViewModel() {
	private val _uiState = MutableStateFlow(LoginScreenUiState())
	val uiState: StateFlow<LoginScreenUiState> = _uiState.asStateFlow()

	fun setUid(uid: String) {
		_uiState.value = _uiState.value.copy(uid = uid.trim())
	}

	fun setPassword(password: String) {
		_uiState.value = _uiState.value.copy(password = password)
	}

	private fun validateFields() {
		if (_uiState.value.password.isEmpty() && _uiState.value.uid.isEmpty()) {
			_uiState.value = _uiState.value.copy(error = "username/email and password are required")
		} else if (_uiState.value.uid.isEmpty()) {
			_uiState.value = _uiState.value.copy(error = "username/email is required")
		} else if (_uiState.value.password.isEmpty()) {
			_uiState.value = _uiState.value.copy(error = "password is required")
		} else {
			_uiState.value = _uiState.value.copy(error = null)
		}
	}

	fun login() {
		viewModelScope.launch {
			_uiState.value = _uiState.value.copy(isLoading = true)
			validateFields()
			if (!_uiState.value.isError) {
				val result = authenticationRepository.login(uiState.value.uid, uiState.value.password)
				result.onFailure {
					_uiState.value = _uiState.value.copy(error = it.message)
				}
			}
			_uiState.value = _uiState.value.copy(isLoading = false)
		}
	}
}