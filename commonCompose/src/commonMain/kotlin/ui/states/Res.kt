package ui.states

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import org.jetbrains.compose.resources.ExperimentalResourceApi
import pdnsmanager.commoncompose.generated.resources.Res

@OptIn(ExperimentalResourceApi::class)
@Composable
fun rememberRawResource(path: String): MutableState<ByteArray> {
    val bytes = remember {
        mutableStateOf(ByteArray(0))
    }

    LaunchedEffect(Unit) {
        bytes.value = Res.readBytes(path)
    }

    return bytes
}