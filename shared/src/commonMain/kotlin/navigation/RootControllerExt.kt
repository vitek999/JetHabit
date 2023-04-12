package navigation

import ru.alexgladkov.odyssey.compose.RootController
import ru.alexgladkov.odyssey.compose.extensions.present

fun RootController.present(screen: Screen) = present(screen.name)