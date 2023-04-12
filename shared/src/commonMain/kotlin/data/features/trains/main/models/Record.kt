package data.features.trains.main.models

import kotlinx.datetime.Instant
import sensors.SensorData

data class Record(
    val id: Long,
    val userId: Long,
    val trainId: Long,
    val date: Instant,
    val data: SensorData? = null,
)
