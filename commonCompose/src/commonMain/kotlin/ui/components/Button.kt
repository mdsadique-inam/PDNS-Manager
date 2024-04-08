package ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ButtonElevation
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon

@Composable
fun PDNSButton(
	onClick: () -> Unit,
	modifier: Modifier = Modifier,
	enabled: Boolean = true,
	loading: Boolean = false,
	shape: Shape = ButtonDefaults.shape,
	colors: ButtonColors = ButtonDefaults.buttonColors(),
	elevation: ButtonElevation? = ButtonDefaults.buttonElevation(),
	border: BorderStroke? = null,
	contentPadding: PaddingValues = ButtonDefaults.ContentPadding,
	content: @Composable RowScope.() -> Unit
) {
	Button(
		modifier = modifier.pointerHoverIcon(
			icon = PointerIcon.Hand
		),
		onClick = onClick,
		enabled = enabled,
		shape = shape,
		colors = colors,
		elevation = elevation,
		border = border,
		contentPadding = contentPadding
	) {
		if (loading) {
			CircularProgressIndicator()
		} else {
			content()
		}
	}
}

@Composable
fun PDNSTextButton(
	onClick: () -> Unit,
	modifier: Modifier = Modifier,
	enabled: Boolean = true,
	loading: Boolean = false,
	shape: Shape = ButtonDefaults.textShape,
	colors: ButtonColors = ButtonDefaults.textButtonColors(),
	elevation: ButtonElevation? = null,
	border: BorderStroke? = null,
	contentPadding: PaddingValues = ButtonDefaults.TextButtonContentPadding,
	content: @Composable RowScope.() -> Unit
) {
	TextButton(
		modifier = modifier.pointerHoverIcon(
			icon = PointerIcon.Hand
		),
		onClick = onClick,
		enabled = enabled,
		shape = shape,
		colors = colors,
		elevation = elevation,
		border = border,
		contentPadding = contentPadding
	) {
		if (loading) {
			CircularProgressIndicator()
		} else {
			content()
		}
	}
}