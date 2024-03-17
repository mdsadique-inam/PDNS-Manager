import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import moe.tlaster.precompose.PreComposeApp
import org.koin.compose.KoinContext
import ui.screens.SplashScreen
import ui.theme.AppTheme

@Composable
fun App() {
    PreComposeApp {
        KoinContext {
            AppTheme {
                Surface(tonalElevation = 5.dp) {
                    SplashScreen()
                }
            }
        }
    }
}
