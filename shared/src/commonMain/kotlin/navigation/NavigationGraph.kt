package navigation

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import ru.alexgladkov.odyssey.compose.extensions.bottomNavigation
import ru.alexgladkov.odyssey.compose.extensions.screen
import ru.alexgladkov.odyssey.compose.extensions.tab
import ru.alexgladkov.odyssey.compose.navigation.RootComposeBuilder
import ru.alexgladkov.odyssey.compose.navigation.bottom_bar_navigation.BottomBarDefaults
import ru.alexgladkov.odyssey.compose.navigation.tabs.TabDefaults
import screens.compose.ComposeScreen
import screens.daily.DailyScreen
import screens.daily.views.HabitCardItemModel
import screens.detail.DetailScreen
import screens.settings.SettingsScreen
import screens.splash.SplashScreen
import screens.stats.StatisticsScreen
import screens.train.detailed.TrainDetailedScreen
import screens.train.main.TrainMainScreen
import screens.train.prediction_results.PredictionResultsScreen
import screens.train.record.TrainRecordScreen
import screens.train.record.TrainRecordScreenArgs
import screens.train.settings.TrainSettingsScreen
import screens.users.create.CreateUserScreen
import screens.users.detailed.UserDetailedScreen
import screens.users.main.UsersMainScreen
import tech.mobiledeveloper.shared.AppRes
import ui.themes.JetHabitTheme

@OptIn(
    ExperimentalComposeUiApi::class,
    ExperimentalFoundationApi::class,
    ExperimentalMaterialApi::class
)
@Composable
internal fun RootComposeBuilder.navigationGraph() {
    screen(NavigationTree.SplashScreen.name) {
        SplashScreen()
    }

    screen(NavigationTree.TrainDataCollector.Main.name) {
        TrainMainScreen()
    }

    screen(NavigationTree.Users.List.name) {
        UsersMainScreen()
    }

    screen(NavigationTree.Users.Create.name) {
        CreateUserScreen()
    }

    screen(NavigationTree.Users.Detailed.name) {
        UserDetailedScreen(it as Long)
    }

    screen(NavigationTree.TrainDataCollector.Detailed.name) {
        TrainDetailedScreen(it as Long)
    }

    screen(NavigationTree.TrainDataCollector.PredictionResults.name) {
        PredictionResultsScreen(it as Long)
    }

    screen(NavigationTree.TrainDataCollector.Record.name) {
        TrainRecordScreen(it as TrainRecordScreenArgs)
    }

    screen(NavigationTree.TrainDataCollector.Settings.name) {
        TrainSettingsScreen()
    }

//
//    bottomNavigation(
//        "main",
//        colors = BottomBarDefaults.bottomColors(
//            backgroundColor = JetHabitTheme.colors.primaryBackground
//        )
//    ) {
//        val colors = TabDefaults.simpleTabColors(
//            selectedColor = JetHabitTheme.colors.primaryText,
//            unselectedColor = JetHabitTheme.colors.controlColor
//        )
//
//        tab(TabDefaults.content(AppRes.string.title_daily, Icons.Filled.DateRange), colors) {
//            screen("daily") {
//                DailyScreen()
//            }
//
//            screen("detail") {
//                DetailScreen(it as HabitCardItemModel)
//            }
//        }
//
//        tab(TabDefaults.content(AppRes.string.title_statistics, Icons.Filled.Star), colors) {
//            screen("statistics") {
//                StatisticsScreen()
//            }
//        }
//
//        tab(TabDefaults.content(AppRes.string.title_settings, Icons.Filled.Settings), colors) {
//            screen("settings") {
//                SettingsScreen()
//            }
//        }
//    }
//
//    screen("compose") {
//        ComposeScreen()
//    }
}

abstract class Screen {
    val name: String
        get() = this::class.qualifiedName!!
}

internal object NavigationTree {
    object SplashScreen : Screen()

    sealed class TrainDataCollector : Screen() {
        object Main : TrainDataCollector()

        object Detailed : TrainDataCollector()
        object Record : TrainDataCollector()

        object Settings : TrainDataCollector()

        object PredictionResults : TrainDataCollector()
    }

    sealed class Users : Screen() {
        object List : Users()
        object Detailed : Users()
        object Create : Users()
    }
}