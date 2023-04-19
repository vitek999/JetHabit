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
    val data: SensorData? = null,
)

@Serializable
data class RecordDto(
    val id: Long,
    val userId: Long,
    val trainId: Long,
    val date: Instant,
    val data: SensorDataDto? = null,
)

fun Record.asDto(): RecordDto = RecordDto(id, userId, trainId, date, data?.asDto())
fun Record.asDto(data: SensorDataDto): RecordDto = RecordDto(id, userId, trainId, date, data)
