package data.features.trains.settings

import com.russhwolf.settings.Settings
import com.russhwolf.settings.set

class SettingsRepository(private val settings: Settings) {
    fun saveDefaultTrainTime(time: Long) {
        settings[SETTINGS_TRAIN_TIME_KEY] = time
    }

    fun getDefaultTrainTime(): Long = settings.getLongOrNull(SETTINGS_TRAIN_TIME_KEY) ?: 20

    companion object {
        private const val SETTINGS_TRAIN_TIME_KEY = "settings_train_time_key"
    }
}