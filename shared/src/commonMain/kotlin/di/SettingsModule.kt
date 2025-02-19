package di

import com.russhwolf.settings.Settings
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.singleton

val settingsModule = DI.Module("settingsModule") {
    bind<Settings>() with singleton {
        Settings()
    }
}