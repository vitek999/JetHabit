package di

import data.features.daily.dailyModule
import data.features.habit.habitModule
import data.features.trains.trainModule
import data.features.users.usersModule
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.direct
import org.kodein.di.singleton
import sensors.CustomSensorManager

object PlatformSDK {

    fun init(
        configuration: PlatformConfiguration,
        customSensorManager: CustomSensorManager,
    ) {
        val umbrellaModule = DI.Module("umbrella") {
            bind<PlatformConfiguration>() with singleton { configuration }
            bind<CustomSensorManager>() with singleton { customSensorManager }
        }

        Inject.createDependencies(
            DI {
                importAll(
                    umbrellaModule,
                    coreModule,
                    dailyModule,
                    habitModule,
                    trainModule,
                    usersModule,
                )
            }.direct
        )
    }
}