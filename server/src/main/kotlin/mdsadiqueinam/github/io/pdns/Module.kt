package mdsadiqueinam.github.io.pdns

import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.resources.*
import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Module
import org.koin.core.annotation.Single

@Module
@ComponentScan
class PDNSModule {

    @Single
    fun client() = HttpClient(CIO) {
        install(Resources)
    }
}