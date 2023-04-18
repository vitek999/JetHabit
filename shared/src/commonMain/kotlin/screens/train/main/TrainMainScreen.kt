package screens.train.main

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import com.adeo.kviewmodel.compose.ViewModel
import com.adeo.kviewmodel.compose.observeAsState
import navigation.NavigationTree
import ru.alexgladkov.odyssey.compose.extensions.push
import ru.alexgladkov.odyssey.compose.local.LocalRootController
import screens.train.main.model.TrainMainScreenAction
import screens.train.main.model.TrainMainScreenEvent
import screens.train.main.view.TrainMainView

@Composable
internal fun TrainMainScreen() {
    val rootController = LocalRootController.current

    ViewModel(factory = { TrainMainScreenViewModel() }) { viewModel ->
        val viewState by viewModel.viewStates().observeAsState()
        val viewAction by viewModel.viewActions().observeAsState()

        TrainMainView(
            viewState = viewState,
            onRowClick = { viewModel.obtainEvent(TrainMainScreenEvent.TrainSelected(it)) },
            onUsersClick = { viewModel.obtainEvent(TrainMainScreenEvent.OnUsersClicked)},
            onBackClick =  { viewModel.obtainEvent(TrainMainScreenEvent.OnBackClicked)},
            onSettingsClick = { viewModel.obtainEvent(TrainMainScreenEvent.OnSettingsClicked) }
        )

        when (val action = viewAction) {
            is TrainMainScreenAction.NavigateToDetailTrain -> {
                viewModel.obtainEvent(TrainMainScreenEvent.ActionInvoked)
                rootController.push(NavigationTree.TrainDataCollector.Detailed.name, params = action.id)
            }
            TrainMainScreenAction.OpenUsersScreen -> {
                rootController.findRootController().push(NavigationTree.Users.List.name)
                viewModel.obtainEvent(TrainMainScreenEvent.ActionInvoked)
            }

            null -> { /* ignore */
            }

            TrainMainScreenAction.NavigateBack -> {
                viewModel.obtainEvent(TrainMainScreenEvent.ActionInvoked)
                rootController.popBackStack()
            }

            TrainMainScreenAction.OpenSettings -> {
                viewModel.obtainEvent(TrainMainScreenEvent.ActionInvoked)
                rootController.push(NavigationTree.TrainDataCollector.Settings.name)
            }
        }
    }
}