package ui.screens

import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.StringResource
import pdnsmanager.composeapp.generated.resources.Res
import pdnsmanager.composeapp.generated.resources.login
import pdnsmanager.composeapp.generated.resources.register

@OptIn(ExperimentalResourceApi::class)
sealed class Screen(val route: String, val resource: StringResource) {
    data object Login : Screen("/login", Res.string.login)
    data object Register : Screen("/register", Res.string.register)
}