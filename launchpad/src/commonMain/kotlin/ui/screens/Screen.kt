package ui.screens

import org.jetbrains.compose.resources.StringResource
import pdnsmanager.commonCompose.resources.Res
import pdnsmanager.commonCompose.resources.login
import pdnsmanager.commonCompose.resources.register

sealed class Screen(val route: String, val resource: StringResource) {
	data object Login : Screen("/login", Res.string.login)
	data object Register : Screen("/register", Res.string.register)
}