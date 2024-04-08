package ui.viewModels

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import models.ValidatedField
import repositories.AuthenticationRepository

data class RegisterScreenUiState(
	val name: String = "",
	val username: String = "",
	val email: String = "",
	val password: String = "",
	val isLoading: Boolean = false,
	val error: String? = null,
	val errors: List<ValidatedField>? = null
) {
	val nameErrors: List<String>? get() = errors?.find { it.field === this::name.name }?.errors
	val usernameErrors: List<String>? get() = errors?.find { it.field === this::username.name }?.errors
	val emailErrors: List<String>? get() = errors?.find { it.field === this::email.name }?.errors
	val passwordErrors: List<String>? get() = errors?.find { it.field === this::password.name }?.errors

	val isError: Boolean get() = error != null || errors?.isNotEmpty() == true
}

class RegisterViewModel(private val authenticationRepository: AuthenticationRepository) : ViewModel() {
	private val _uiState = MutableStateFlow(RegisterScreenUiState())
	val uiState = _uiState.asStateFlow()

	fun setName(name: String) {
		_uiState.value = _uiState.value.copy(name = name.trim())
	}

	fun setUsername(username: String) {
		_uiState.value = _uiState.value.copy(username = username.trim())
	}

	fun setEmail(email: String) {
		_uiState.value = _uiState.value.copy(email = email.trim())
	}

	fun setPassword(password: String) {
		_uiState.value = _uiState.value.copy(password = password)
	}

	private fun validateFields() {
		val errors = mutableListOf<ValidatedField>()
		if (_uiState.value.name.isEmpty()) {
			errors.add(ValidatedField(RegisterScreenUiState::name.name, listOf("Name is required")))
		}
		if (_uiState.value.username.isEmpty()) {
			errors.add(ValidatedField(RegisterScreenUiState::username.name, listOf("Username is required")))
		}
		if (_uiState.value.email.isEmpty()) {
			errors.add(ValidatedField(RegisterScreenUiState::email.name, listOf("Email is required")))
		}
		if (_uiState.value.password.isBlank()) {
			errors.add(ValidatedField(RegisterScreenUiState::password.name, listOf("Password is required")))
		}

		_uiState.value = _uiState.value.copy(errors = errors)
	}

	fun register() {
		validateFields()
	}
}