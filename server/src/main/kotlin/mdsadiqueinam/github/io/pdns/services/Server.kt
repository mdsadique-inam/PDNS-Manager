package mdsadiqueinam.github.io.pdns.services

import io.ktor.client.*
import org.koin.core.annotation.Single

@Single
class Server(private val client: HttpClient) {
    fun start() {
        println("Starting server...")
    }
}