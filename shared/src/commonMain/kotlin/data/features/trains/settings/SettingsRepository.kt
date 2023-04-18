package data.features.trains.settings

import com.russhwolf.settings.Settings
import com.russhwolf.settings.set

class SettingsRepository(private val settings: Settings) {
    fun saveDefaultTrainTime(time: Int) {
        settings[SETTINGS_TRAIN_TIME_KEY] = time
    }

    fun getDefaultTrainTime(): Int = settings.getIntOrNull(SETTINGS_TRAIN_TIME_KEY) ?: 20

    companion object {
        private const val SETTINGS_TRAIN_TIME_KEY = "settings_train_time_key"
    }
}