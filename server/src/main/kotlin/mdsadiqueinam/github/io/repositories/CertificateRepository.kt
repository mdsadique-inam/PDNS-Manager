package mdsadiqueinam.github.io.repositories

import java.net.Inet6Address
import java.net.NetworkInterface

fun getConnectedNetworkIPv6(): String? {
    val networkInterfaces = NetworkInterface.getNetworkInterfaces()

    networkInterfaces.asSequence().forEach { networkInterface ->
        networkInterface.inetAddresses.asSequence()
            .filter { it is Inet6Address && !it.isLoopbackAddress && !it.isSiteLocalAddress && !it.isLinkLocalAddress }
            .forEach {
                val hostAddress = it.hostAddress
                val index = hostAddress.indexOf('%')
                return if (index > -1) hostAddress.substring(0, index) else hostAddress
            }
    }

    return null
}