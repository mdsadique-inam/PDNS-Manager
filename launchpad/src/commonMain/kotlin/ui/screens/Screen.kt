package ui.screens

import org.jetbrains.compose.resources.StringResource
import pdnsmanager.launchpad.generated.resources.Res
import pdnsmanager.launchpad.generated.resources.login
import pdnsmanager.launchpad.generated.resources.register

sealed class Screen(val route: String, val resource: StringResource) {
	data object Login : Screen("/login", Res.string.login)
	data object Register : Screen("/register", Res.string.register)
}