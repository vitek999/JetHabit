package data.features.trains.main

import Database
import data.RecordEntity
import data.SensorDataEntity
import data.features.trains.main.models.Record
import data.features.trains.main.models.Train
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import sensors.AccelerometerValue
import sensors.GyroscopeValue
import sensors.SensorData

class TrainRepository(private val database: Database) {
    private val trains: List<Train> = listOf(
        Train(1L,  "Приседания"),
        Train(2L,  "Отжимания"),
        Train(3L,  "Прыжки"),
        Train(4L,  "Выпады"),
        Train(5L,  "Прес"),
    )

    suspend fun fetchAllTrains(): List<Train> = trains

    suspend fun fetchTrainById(id: Long): Train? = trains.firstOrNull { it.id == id }

    suspend fun fetchAllRecords(): List<Record> = database.recordsQueries.selectAll().executeAsList().map { it.asModel() }

    suspend fun fetchByTrainIdAndUserId(userId: Long, trainId: Long): List<Record> = database.recordsQueries.selectByUserAndTrain(
        userId = userId,
        trainId = trainId
    ).executeAsList().map { it.asModel() }

    suspend fun fetchSensorDataByTrain(recordId: Long) : SensorData  {
        val accData = database.sensor_dataQueries
            .selectAllByRecordIdAndSensorType(recordId, 0)
            .executeAsList()
            .map { it.asAccelerometerValue() }
        val gyrData = database.sensor_dataQueries
            .selectAllByRecordIdAndSensorType(recordId, 1)
            .executeAsList().map { it.asGyroscopeValue() }
        return SensorData(accData, gyrData)
    }

    suspend fun addRecord(userId: Long, trainId: Long, data: SensorData) {
        val id = database.recordsQueries.selectLastId().executeAsOneOrNull()?.let { it + 1 } ?: 0
        val time = Clock.System.now().toEpochMilliseconds()
        database.recordsQueries.insert(
            id = id,
            userId = userId,
            trainId = trainId,
            timestamp = time,
        )
        database.sensor_dataQueries.transaction {
            afterCommit { println("TEST: all inserted") }

            data.accelerometerValue.forEach {
                database.sensor_dataQueries.insert(
                    recordId = id,
                    sensorType = 0L,
                    timestamp = it.timestamp,
                    gx = it.gx.toDouble(),
                    gy = it.gy.toDouble(),
                    gz = it.gz.toDouble()
                )
            }

            data.gyroscopeValue.forEach {
                database.sensor_dataQueries.insert(
                    recordId = id,
                    sensorType = 1L,
                    timestamp = it.timestamp,
                    gx = it.gx.toDouble(),
                    gy = it.gy.toDouble(),
                    gz = it.gz.toDouble()
                )
            }
        }
    }

    companion object  {
        private fun RecordEntity.asModel(): Record = Record(
            id = id,
            userId = userId,
            trainId = trainId,
            date = Instant.fromEpochMilliseconds(timestamp),
        )

        private fun SensorDataEntity.asAccelerometerValue(): AccelerometerValue = AccelerometerValue(
            timestamp  =timestamp,
            gx = gx.toFloat(),
            gy = gy.toFloat(),
            gz = gz.toFloat(),
        )

        private fun SensorDataEntity.asGyroscopeValue(): GyroscopeValue = GyroscopeValue(
            timestamp  =timestamp,
            gx = gx.toFloat(),
            gy = gy.toFloat(),
            gz = gz.toFloat(),
        )
    }
}