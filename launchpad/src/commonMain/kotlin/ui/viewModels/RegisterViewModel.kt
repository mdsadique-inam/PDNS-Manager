package ui.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import models.ApiResponse
import models.RegisterBody
import models.ValidatedField
import repositories.AuthenticationRepository

data class RegisterScreenUiState(
	val name: String = "",
	val username: String = "",
	val email: String = "",
	val password: String = "",
	val confirmPassword: String = "",
	val isLoading: Boolean = false,
	val error: String? = null,
	val errors: List<ValidatedField>? = null
) {
	val nameErrors: List<String>? get() = errors?.find { it.field == this::name.name }?.errors
	val usernameErrors: List<String>? get() = errors?.find { it.field == this::username.name }?.errors
	val emailErrors: List<String>? get() = errors?.find { it.field == this::email.name }?.errors
	val passwordErrors: List<String>? get() = errors?.find { it.field == this::password.name }?.errors
	val confirmPasswordErrors: List<String>? get() = errors?.find { it.field == this::confirmPassword.name }?.errors

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

	fun setConfirmPassword(password: String) {
		_uiState.value = _uiState.value.copy(confirmPassword = password)
	}

	private fun validateFields() {
		val errors = mutableListOf<ValidatedField>()
		if (uiState.value.name.isEmpty()) {
			errors.add(ValidatedField(RegisterScreenUiState::name.name, listOf("Name is required")))
		}
		if (uiState.value.username.isEmpty()) {
			errors.add(ValidatedField(RegisterScreenUiState::username.name, listOf("Username is required")))
		}
		if (uiState.value.email.isEmpty()) {
			errors.add(ValidatedField(RegisterScreenUiState::email.name, listOf("Email is required")))
		}
		if (uiState.value.password.isBlank()) {
			errors.add(ValidatedField(RegisterScreenUiState::password.name, listOf("Password is required")))
		}
		if (uiState.value.password != uiState.value.confirmPassword) {
			errors.add(ValidatedField(RegisterScreenUiState::confirmPassword.name, listOf("Passwords don't match")))
		}

		_uiState.value = _uiState.value.copy(errors = errors)
	}

	fun register() {
		viewModelScope.launch {
			_uiState.value = _uiState.value.copy(isLoading = true)
			validateFields()
			if (!uiState.value.isError) {
				val result = authenticationRepository.register(
					RegisterBody(
						name = uiState.value.name,
						username = uiState.value.username,
						email = uiState.value.email,
						password = uiState.value.password
					)
				)
				result.apply {
					onSuccess { response ->
						if (response is ApiResponse.Error) {
							_uiState.value = _uiState.value.copy(error = response.message, errors = response.errors)
						}
					}
					onFailure { error ->
						_uiState.value = _uiState.value.copy(error = error.message)
					}
				}
			}
			_uiState.value = _uiState.value.copy(isLoading = false)
		}
	}
}