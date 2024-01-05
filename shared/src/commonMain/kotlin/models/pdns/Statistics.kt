package models.pdns

import kotlinx.serialization.Serializable

@Serializable
data class StatisticItem(
    val name: String,
    val type: String,
    val value: String
)

@Serializable
data class MapStatisticItem(
    val name: String,
    val type: String,
    val value: List<SimpleStatisticItem>
)

@Serializable
data class RingStatisticItem(
    val name: String,
    val type: String,
    val size: Int,
    val value: List<SimpleStatisticItem>
)

@Serializable
data class SimpleStatisticItem(
    val name: String,
    val value: String
)