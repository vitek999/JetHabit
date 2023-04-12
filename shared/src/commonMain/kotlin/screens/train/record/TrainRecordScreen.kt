package screens.train.record

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import com.adeo.kviewmodel.compose.ViewModel
import com.adeo.kviewmodel.compose.observeAsState
import ru.alexgladkov.odyssey.compose.local.LocalRootController
import screens.train.record.model.TrainRecordAction
import screens.train.record.model.TrainRecordEvent
import screens.train.record.view.TrainRecordView

data class TrainRecordScreenArgs(
    val trainId: Long,
    val userId: Long,
)

@Composable
fun TrainRecordScreen(args: TrainRecordScreenArgs) {
    val rootController = LocalRootController.current

    ViewModel(factory = { TrainRecordViewModel(trainId = args.trainId, userId = args.userId) }) { viewModel ->
        val viewState by viewModel.viewStates().observeAsState()
        val viewAction by viewModel.viewActions().observeAsState()

        TrainRecordView(
            viewState = viewState,
            onBackClicked = { viewModel.obtainEvent(TrainRecordEvent.OnBackPressed) },
            onStarClick = { viewModel.obtainEvent(TrainRecordEvent.OnStartRecordClick) },
            onStopClick = { viewModel.obtainEvent(TrainRecordEvent.OnStopRecordClick) }
        )

        when(viewAction) {
            TrainRecordAction.NavigateBack -> {
                viewModel.obtainEvent(TrainRecordEvent.ActionInvoked)
                rootController.popBackStack()
            }
            null -> {

            }
        }
    }
}