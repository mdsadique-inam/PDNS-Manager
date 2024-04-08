package extensions

import org.jetbrains.compose.resources.ExperimentalResourceApi
import pdnsmanager.launchpad.generated.resources.Res

object RawRes {
    const val internet_loader = "files/lottie/loader_internet.json"
}

@OptIn(ExperimentalResourceApi::class)
internal val Res.raw get() = RawRes