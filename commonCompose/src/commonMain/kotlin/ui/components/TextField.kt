package ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.outlined.Visibility
import androidx.compose.material.icons.outlined.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.KeyEventType
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onPreviewKeyEvent
import androidx.compose.ui.input.key.type
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import org.jetbrains.compose.resources.stringResource
import pdnsmanager.commoncompose.generated.resources.Res
import pdnsmanager.commoncompose.generated.resources.error
import pdnsmanager.commoncompose.generated.resources.hide_password
import pdnsmanager.commoncompose.generated.resources.show_password

@Composable
fun PMCOutlinedTextField(
	value: String,
	onValueChange: (String) -> Unit,
	modifier: Modifier = Modifier,
	enabled: Boolean = true,
	readOnly: Boolean = false,
	label: @Composable (() -> Unit)? = null,
	placeholder: @Composable (() -> Unit)? = null,
	leadingIcon: @Composable (() -> Unit)? = null,
	trailingIcon: @Composable (() -> Unit)? = null,
	prefix: @Composable (() -> Unit)? = null,
	suffix: @Composable (() -> Unit)? = null,
	supportingText: @Composable (() -> Unit)? = null,
	error: String? = null,
	isError: Boolean = error != null,
	visualTransformation: VisualTransformation = VisualTransformation.None,
) {
	val focusManager = LocalFocusManager.current
	OutlinedTextField(
		modifier = modifier.onPreviewKeyEvent {
			if (it.key == Key.Tab && it.type == KeyEventType.KeyDown){
				focusManager.moveFocus(FocusDirection.Down)
				true
			} else {
				false
			}
		},
		value = value,
		onValueChange = onValueChange,
		label = label,
		enabled = enabled,
		readOnly = readOnly,
		isError = isError,
		placeholder = placeholder,
		leadingIcon = leadingIcon,
		trailingIcon = {
			Row(
				verticalAlignment = Alignment.CenterVertically
			) {
				if (isError) {
					Icon(Icons.Filled.Error, "error", tint = MaterialTheme.colorScheme.error)
				}
				trailingIcon?.invoke()
			}
		},
		prefix = prefix,
		suffix = suffix,
		supportingText = {
			Column(
				horizontalAlignment = Alignment.Start
			) {
				if (isError && error != null) {
					Text(
						modifier = Modifier.fillMaxWidth(),
						text = error,
						color = MaterialTheme.colorScheme.error
					)
				}
				supportingText?.invoke()
			}
		},
		visualTransformation = visualTransformation,
		keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
		keyboardActions = KeyboardActions(
			onNext = { focusManager.moveFocus(FocusDirection.Down) }
		)
	)
}


sealed class FieldVisibility(
	val visualTransformation: VisualTransformation,
	val icon: @Composable() (() -> Unit),
) {
	data object Visible : FieldVisibility(
		visualTransformation = VisualTransformation.None,
		icon = {
			Icon(Icons.Outlined.Visibility, contentDescription = stringResource(Res.string.show_password))
		}
	)

	data object Hidden : FieldVisibility(
		visualTransformation = PasswordVisualTransformation(),
		icon = {
			Icon(Icons.Outlined.VisibilityOff, contentDescription = stringResource(Res.string.hide_password))
		}
	)
}

@Composable
fun PMCPasswordTextField(
	value: String,
	onValueChange: (String) -> Unit,
	label: @Composable (() -> Unit)? = null,
	modifier: Modifier = Modifier,
	error: String? = null,
	isError: Boolean = error != null,
) {
	val visibility = remember { mutableStateOf<FieldVisibility>(FieldVisibility.Hidden) }
	PMCOutlinedTextField(
		value = value,
		onValueChange = onValueChange,
		label = label,
		modifier = modifier,
		isError = isError,
		error = error,
		trailingIcon = {
			IconButton(
				onClick = {
					if (visibility.value == FieldVisibility.Hidden) {
						visibility.value = FieldVisibility.Visible
					} else {
						visibility.value = FieldVisibility.Hidden
					}
				},
				modifier = Modifier.pointerHoverIcon(PointerIcon.Hand)
			) {
				visibility.value.icon()
			}
		},
		visualTransformation = visibility.value.visualTransformation,
	)
}