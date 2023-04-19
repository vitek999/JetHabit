package screens.train.settings

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import com.adeo.kviewmodel.compose.ViewModel
import com.adeo.kviewmodel.compose.observeAsState
import ru.alexgladkov.odyssey.compose.local.LocalRootController
import screens.train.settings.model.TrainSettingsAction
import screens.train.settings.model.TrainSettingsEvent
import screens.train.settings.view.TrainSettingsView

@Composable
fun TrainSettingsScreen() {
    val rootController = LocalRootController.current

    ViewModel(factory = { TrainSettingsViewModel() }) { viewModel ->
        val viewState by viewModel.viewStates().observeAsState()
        val viewAction by viewModel.viewActions().observeAsState()

        TrainSettingsView(
            viewState = viewState,
            onBackClick = { viewModel.obtainEvent(TrainSettingsEvent.OnBackClicked) },
            onSaveClick = { viewModel.obtainEvent(TrainSettingsEvent.OnSaveClicked) },
            onTrainTimeChange = { viewModel.obtainEvent(TrainSettingsEvent.TrainTimeChanged(it)) },
            onExportClick = { viewModel.obtainEvent(TrainSettingsEvent.OnExportUserDataClicked) }
        )

        when (viewAction) {
            TrainSettingsAction.NavigateBack -> {
                viewModel.obtainEvent(TrainSettingsEvent.ActionInvoked)
                rootController.popBackStack()
            }

            null -> {}
        }
    }
}