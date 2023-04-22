package data.features.trains.main.models

import kotlinx.datetime.Instant
import kotlinx.serialization.Serializable
import sensors.SensorData
import sensors.SensorDataDto
import sensors.asDto

data class Record(
    val id: Long,
    val userId: Long,
    val trainId: Long,
    val date: Instant,
    val duration: Long,
    val data: SensorData? = null,
    val timestamps: List<Long> = emptyList(),
)

@Serializable
data class RecordDto(
    val id: Long,
    val userId: Long,
    val trainId: Long,
    val date: Instant,
    val duration: Long,
    val data: SensorDataDto? = null,
    val timestamps: List<Long> = emptyList(),
)

fun Record.asDto(): RecordDto = RecordDto(id, userId, trainId, date, duration, data?.asDto(), timestamps)
fun Record.asDto(data: SensorDataDto, timestamps: List<Long>): RecordDto = RecordDto(id, userId, trainId, date, duration, data, timestamps)
