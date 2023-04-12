package data.features.trains

import data.features.trains.main.TrainRepository
import org.kodein.di.DI
import org.kodein.di.bindSingleton
import org.kodein.di.instance

val trainModule = DI.Module("train") {
    bindSingleton<TrainRepository> { TrainRepository(instance()) }
}