package di

import io.ktor.client.*
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.singleton

val httpModule = DI.Module("httpModule") {
  bind<HttpClient>() with singleton { HttpClient() }
}