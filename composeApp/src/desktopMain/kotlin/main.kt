import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.runtime.Composable
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import di.AppModule
import org.koin.core.context.startKoin

fun main() {
    startKoin {
         modules(AppModule.module)
    }
    application {
        Window(onCloseRequest = ::exitApplication, title = "PDNS Manager") {
            App()
        }
    }
}

@Preview
@Composable
fun AppDesktopPreview() {
    App()
}