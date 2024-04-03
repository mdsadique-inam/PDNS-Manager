import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import org.koin.compose.KoinContext
import ui.screens.LoginScreen
import ui.theme.AppTheme

@Composable
fun App() {
    KoinContext {
        AppTheme {
            Surface(tonalElevation = 5.dp) {
                LoginScreen()
            }
        }
    }
}