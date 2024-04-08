package ui.components

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon

@Composable
fun PMCButton(
	onClick: () -> Unit,
	modifier: Modifier = Modifier,
	enabled: Boolean = true,
	loading: Boolean = false,
	content: @Composable RowScope.() -> Unit
) {
	Button(
		modifier = modifier.pointerHoverIcon(
			icon = PointerIcon.Hand
		),
		onClick = onClick,
		enabled = enabled,
	) {
		if (loading) {
			CircularProgressIndicator()
		} else {
			content()
		}
	}
}

@Composable
fun PMCTextButton(
	onClick: () -> Unit,
	modifier: Modifier = Modifier,
	enabled: Boolean = true,
	loading: Boolean = false,
	content: @Composable RowScope.() -> Unit
) {
	TextButton(
		modifier = modifier.pointerHoverIcon(
			icon = PointerIcon.Hand
		),
		onClick = onClick,
		enabled = enabled,
	) {
		if (loading) {
			CircularProgressIndicator()
		} else {
			content()
		}
	}
}