package tech.mobiledeveloper.jethabit

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.util.Log
import sensors.AccelerometerValue
import sensors.CustomSensorManager
import sensors.GyroscopeValue
import sensors.SensorData
import kotlin.time.Duration
import kotlin.time.DurationUnit
import kotlin.time.toDuration

class AndroidCustomSensorManager(private val context: Context) : CustomSensorManager {

    private var sensorManager: SensorManager? = null
    private var sensorListener: SensorEventListener? = null
    private var callback: CustomSensorManager.CustomSensorListener? = null
    private var firstTimeStamp: Long = 0L
    private val acCache = mutableListOf<AccelerometerValue>()
    private val gyroCache = mutableListOf<GyroscopeValue>()

    override fun start(time: Duration) {
        val manager =
            (context.getSystemService(Context.SENSOR_SERVICE) as SensorManager).also {
                sensorManager = it
            }

        firstTimeStamp = 0L
        acCache.clear()
        gyroCache.clear()
        val callback = (object : SensorEventListener {
            private val accelerometerReading = FloatArray(3)
            private val magnetometerReading = FloatArray(3)
            private val linear_acceleration = FloatArray(3)
            val alpha = 0.8f
//            private val rotationMatrix = FloatArray(9)
//            private val orientationAngles = FloatArray(3)

//            private var lastSin = 0.0
//            private var lastCos = 1.0
//            private var lastDegrees = 0.0

            override fun onSensorChanged(newEvent: SensorEvent?) {
                val event = newEvent ?: return
                val gravity = FloatArray(3) { SensorManager.GRAVITY_EARTH }

                if (firstTimeStamp == 0L) {
                    firstTimeStamp = event.timestamp
                }

                val deltaTimeStamp = event.timestamp - firstTimeStamp
                val timeLimit = time.inWholeNanoseconds

                if (deltaTimeStamp < timeLimit) {
                    Log.e(
                        TAG,
                        "onSensorChanged: ${deltaTimeStamp}:Gx${event.values[0]};Gy${event.values[1]};Gz${event.values[2]}"
                    )
                    if (event.sensor.type == Sensor.TYPE_ACCELEROMETER) {
                        if (!IS_NORMAL) {
                            acCache.add(
                                AccelerometerValue(
                                    deltaTimeStamp,
                                    event.values[0],
                                    event.values[1],
                                    event.values[2],
                                )
                            )
                        } else {
                            gravity[0] = alpha * gravity[0] + (1 - alpha) * event.values[0]
                            gravity[1] = alpha * gravity[1] + (1 - alpha) * event.values[1]
                            gravity[2] = alpha * gravity[2] + (1 - alpha) * event.values[2]

                            linear_acceleration[0] = event.values[0] - gravity[0]
                            linear_acceleration[1] = event.values[1] - gravity[1]
                            linear_acceleration[2] = event.values[2] - gravity[2]

                            acCache.add(
                                AccelerometerValue(
                                    deltaTimeStamp.toDuration(DurationUnit.NANOSECONDS).inWholeSeconds,
                                    linear_acceleration[0],
                                    linear_acceleration[1],
                                    linear_acceleration[2],
                                )
                            )
                        }
                    } else if (event.sensor.type == Sensor.TYPE_GYROSCOPE) {
                        gyroCache.add(
                            GyroscopeValue(
                                deltaTimeStamp,
                                event.values[0],
                                event.values[1],
                                event.values[2],
                            )
                        )
                    }

                } else {
                    Log.e(TAG, "onSensorChanged: callback")
                    callback?.newValue(SensorData(acCache, gyroCache))
                    stop()
                }
            }

            override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
                Log.e(TAG, "onAccuracyChanged: $sensor, $accuracy (1=LOW-3=HIGH)")
            }
        }).also { sensorListener = it }


        manager.getDefaultSensor(Sensor.TYPE_GYROSCOPE)?.let { sensor ->
            manager.registerListener(
                callback,
                sensor,
                SAMPLING_PERIOD
            )
        }
        manager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)?.let { sensor ->
            manager.registerListener(
                callback,
                sensor,
                SAMPLING_PERIOD
            )
        }
    }

    override fun stop() {
        sensorManager?.let {
            sensorListener?.let(it::unregisterListener)
        }
        sensorListener = null
        sensorManager = null
    }

    override fun destroy() {
        stop()
        clearListener()
    }

    override fun setListener(listener: CustomSensorManager.CustomSensorListener) {
        callback = listener
    }

    override fun clearListener() {
        callback = null
    }

    companion object {
        private const val TAG = "CustomSensorManager"
        private const val SAMPLING_PERIOD = 5000 // 200Hz

        private const val IS_NORMAL: Boolean = false


    }
}