import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import org.koin.compose.KoinContext
import ui.screens.LoginScreen
import ui.screens.RegisterScreen
import ui.screens.Screen
import ui.theme.AppTheme

@Composable
fun App() {
	KoinContext {
		AppTheme {
			Surface(tonalElevation = 5.dp) {
				PDNSApp()
			}
		}
	}
}

@Composable
fun PDNSApp(navController: NavHostController = rememberNavController()) {
	Scaffold { innerPadding ->
		NavHost(
			navController = navController,
			startDestination = Screen.Register.route,
			modifier = Modifier
				.fillMaxSize()
				.verticalScroll(rememberScrollState())
				.padding(innerPadding)
		) {
			composable(Screen.Login.route) {
				LoginScreen(navigateToRegister = {
					navController.navigate(Screen.Register.route)
				})
			}
			composable(Screen.Register.route) {
				RegisterScreen(navigateToLogin = {
					navController.navigate(Screen.Login.route)
				})
			}
		}
	}
}