package models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

enum class StatisticType {
    @SerialName("StatisticItem") STATISTIC_ITEM,
    @SerialName("MapStatisticItem") MAP_STATISTIC_ITEM,
    @SerialName("RingStatisticItem") RING_STATISTIC_ITEM,
}

@Serializable
sealed class Statistic {
    abstract val name: String

    @Serializable
    @SerialName("StatisticItem")
    data class StatisticItem(
        override val name: String,
        val value: String
    ) : Statistic()

    @Serializable
    @SerialName("MapStatisticItem")
    data class MapStatisticItem(
        override val name: String,
        val value: List<StatisticItem>
    ) : Statistic()

    @Serializable
    @SerialName("RingStatisticItem")
    data class RingStatisticItem(
        override val name: String,
        val size: Int,
        val value: List<StatisticItem>
    ) : Statistic()
}
