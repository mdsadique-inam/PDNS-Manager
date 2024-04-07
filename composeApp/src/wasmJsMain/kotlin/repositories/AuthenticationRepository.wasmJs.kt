package repositories

import kotlinx.browser.window

actual fun AuthenticationRepository.redirectToApp() {
	window.location.href = "app"
}