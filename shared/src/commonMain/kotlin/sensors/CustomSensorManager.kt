package sensors

import kotlin.time.Duration

abstract class SensorValue {
    abstract val timestamp: Long
}

abstract class ThreeAxisSensorValue : SensorValue() {
    abstract val gx: Float
    abstract val gy: Float
    abstract val gz: Float
}

data class AccelerometerValue(
    override val timestamp: Long,
    override val gx: Float,
    override val gy: Float,
    override val gz: Float,
) : ThreeAxisSensorValue()

data class GyroscopeValue(
    override val timestamp: Long,
    override val gx: Float,
    override val gy: Float,
    override val gz: Float,
) : ThreeAxisSensorValue()

data class SensorData(
    val accelerometerValue: List<AccelerometerValue>,
    val gyroscopeValue: List<GyroscopeValue>
) {

    val size: Int
        get() = accelerometerValue.size + gyroscopeValue.size

    enum class Type(val text: String) {
        ACCELEROMETER("Акселерометр"),
        GYROSCOPE("Гироскоп"),
    }
}


interface CustomSensorManager {
    fun start(time: Duration)
    fun stop()
    fun destroy()

    fun setListener(listener: CustomSensorListener)

    fun clearListener()

    interface CustomSensorListener {
        fun newValue(data: SensorData)
    }
}