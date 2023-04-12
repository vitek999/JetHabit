package data.features.users

import org.kodein.di.DI
import org.kodein.di.bindSingleton
import org.kodein.di.instance

val usersModule = DI.Module("users") {
    bindSingleton<UserRepository> { UserRepository(instance()) }
}