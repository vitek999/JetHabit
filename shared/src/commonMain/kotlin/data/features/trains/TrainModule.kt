package data.features.trains

import data.features.trains.main.TrainRepository
import data.features.trains.settings.SettingsRepository
import org.kodein.di.DI
import org.kodein.di.bindSingleton
import org.kodein.di.instance

val trainModule = DI.Module("train") {
    bindSingleton<TrainRepository> { TrainRepository(instance()) }
    bindSingleton<SettingsRepository> { SettingsRepository(instance()) }
}