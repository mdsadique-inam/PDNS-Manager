import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.PermanentDrawerSheet
import androidx.compose.material3.PermanentNavigationDrawer
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import moe.tlaster.precompose.PreComposeApp
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.KoinContext
import pdnsmanager.composeapp.generated.resources.Res
import pdnsmanager.composeapp.generated.resources.compose_multiplatform
import ui.AppTheme

@OptIn(ExperimentalResourceApi::class)
@Composable
fun App() {
    PreComposeApp {
        KoinContext {
            AppTheme {
                Surface(tonalElevation = 5.dp) {
                    val items = listOf(Icons.Default.Favorite, Icons.Default.Face, Icons.Default.Email)
                    val selectedItem = remember { mutableStateOf(items[0]) }
                    var showContent by remember { mutableStateOf(false) }
                    val greeting = remember { Greeting().greet() }
                    PermanentNavigationDrawer(
                        drawerContent = {
                            PermanentDrawerSheet(modifier = Modifier.width(240.dp)) {
                                Spacer(Modifier.height(12.dp))
                                items.forEach { item ->
                                    NavigationDrawerItem(
                                        icon = { Icon(item, contentDescription = null) },
                                        label = { Text(item.name) },
                                        selected = item == selectedItem.value,
                                        onClick = {
                                            selectedItem.value = item
                                        },
                                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 2.dp)
                                    )
                                }
                            }
                        },
                        content = {
                            Column(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(16.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
                                    Button(onClick = { showContent = !showContent }) {
                                        Text("Click me!")
                                    }
                                    AnimatedVisibility(showContent) {
                                        Column(
                                            Modifier.fillMaxWidth(),
                                            horizontalAlignment = Alignment.CenterHorizontally
                                        ) {
                                            Image(painterResource(Res.drawable.compose_multiplatform), null)
                                            Text("Compose: $greeting ${selectedItem.value.name}")
                                        }
                                    }
                                }
                            }
                        }
                    )
                }
            }
        }
    }
}