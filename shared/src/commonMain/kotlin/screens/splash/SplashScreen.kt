package screens.splash

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.adeo.kviewmodel.compose.ViewModel
import di.LocalPlatform
import navigation.NavigationTree
import navigation.present
import ru.alexgladkov.odyssey.compose.local.LocalRootController
import tech.mobiledeveloper.shared.AppRes
import ui.themes.JetHabitTheme

@Composable
internal fun SplashScreen() {
    val rootController = LocalRootController.current
    val platform = LocalPlatform.current


    ViewModel(factory = { SplashScreenViewModel() }) { viewModel ->
        val viewAction by viewModel.viewActions().collectAsState(null)

        SplashScreenView()

        when (viewAction) {
            SplashScreenAction.NavigateToMainMenuScreen -> {
                rootController.present(NavigationTree.TrainDataCollector.Main)
            }

            null -> { /*ignore*/ }
        }

        LaunchedEffect(key1 = Unit) {
            viewModel.obtainEvent(SplashScreenEvent.SplashScreenDisplayed)
        }
    }
}


@Composable
private fun SplashScreenView(modifier: Modifier = Modifier) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(JetHabitTheme.colors.primaryBackground)
    ) {
        Column(modifier = Modifier.align(Alignment.Center)) {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = AppRes.string.splash_screen_title,
                style = JetHabitTheme.typography.heading,
                color = JetHabitTheme.colors.primaryText,
                textAlign = TextAlign.Center
            )
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 4.dp),
                text = AppRes.string.splash_screen_description,
                style = JetHabitTheme.typography.body,
                color = JetHabitTheme.colors.secondaryText,
                textAlign = TextAlign.Center
            )
        }
    }
}