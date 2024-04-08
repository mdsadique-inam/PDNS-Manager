package ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Visibility
import androidx.compose.material.icons.outlined.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import org.jetbrains.compose.resources.stringResource
import pdnsmanager.commoncompose.generated.resources.Res
import pdnsmanager.commoncompose.generated.resources.hide_password
import pdnsmanager.commoncompose.generated.resources.show_password

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
fun PasswordTextField(
	value: String,
	onValueChange: (String) -> Unit,
	label: @Composable (() -> Unit)? = null,
	modifier: Modifier = Modifier,
	isError: Boolean = false,
) {
	val visibility = remember { mutableStateOf<FieldVisibility>(FieldVisibility.Hidden) }
	OutlinedTextField(
		value = value,
		onValueChange = onValueChange,
		label = label,
		modifier = modifier,
		isError = isError,
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