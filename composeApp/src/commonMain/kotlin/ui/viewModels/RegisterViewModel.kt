package ui.viewModels

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import models.ValidatedField

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

	val isError: Boolean get() = error != null
}

class RegisterViewModel : ViewModel() {
	private val _uiState = MutableStateFlow(RegisterScreenUiState())
	val uiState = _uiState.asStateFlow()

}