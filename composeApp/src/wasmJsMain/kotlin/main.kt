import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.CanvasBasedWindow
import di.AppModule
import org.koin.core.context.startKoin

@OptIn(ExperimentalComposeUiApi::class)
fun main() {
    startKoin {
        modules(AppModule.modules)
    }
    CanvasBasedWindow(canvasElementId = "ComposeTarget") { App() }
}