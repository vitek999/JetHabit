package screens.train.detailed

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import com.adeo.kviewmodel.compose.ViewModel
import com.adeo.kviewmodel.compose.observeAsState
import navigation.NavigationTree
import ru.alexgladkov.odyssey.compose.extensions.push
import ru.alexgladkov.odyssey.compose.local.LocalRootController
import screens.train.detailed.model.TrainDetailedAction
import screens.train.detailed.model.TrainDetailedEvent
import screens.train.detailed.view.TrainDetailedView
import screens.train.record.TrainRecordScreenArgs

@Composable
fun TrainDetailedScreen(trainId: Long) {
    val rootController = LocalRootController.current

    ViewModel(factory = { TrainDetailedViewModel(trainId) }) { viewModel ->
        val viewState by viewModel.viewStates().observeAsState()
        val viewAction by viewModel.viewActions().observeAsState()

        TrainDetailedView(
            viewState = viewState,
            onBackClick = { viewModel.obtainEvent(TrainDetailedEvent.OnBackClicked) },
            onRecordClick = { viewModel.obtainEvent(TrainDetailedEvent.OnRecordClicked) },
            onExportClick = { viewModel.obtainEvent(TrainDetailedEvent.OnExportClicked) },
            omSavedRecordClick = { viewModel.obtainEvent(TrainDetailedEvent.OnSavedRecordClicked(it))},
            onDeleteClick = { viewModel.obtainEvent(TrainDetailedEvent.OnDeleteClicked(it)) }
        )

        when(val action = viewAction) {
            TrainDetailedAction.NavigateBack -> {
                viewModel.obtainEvent(TrainDetailedEvent.ActionInvoked)
                rootController.popBackStack()
            }
            null -> { /**/ }
            is TrainDetailedAction.NavigateRecord -> {
                 rootController.push(
                     screen = NavigationTree.TrainDataCollector.Record.name,
                     params = TrainRecordScreenArgs(
                         trainId = action.trainId,
                         userId = action.userId
                     )
                 )
            }
            is TrainDetailedAction.OpenRecordPrediction -> {
                rootController.push(NavigationTree.TrainDataCollector.PredictionResults.name, params = action.recordId)
            }
        }
    }
}
