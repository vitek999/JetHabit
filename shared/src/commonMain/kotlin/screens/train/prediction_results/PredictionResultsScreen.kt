package screens.train.prediction_results

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.adeo.kviewmodel.compose.ViewModel
import com.adeo.kviewmodel.compose.observeAsState
import ru.alexgladkov.odyssey.compose.local.LocalRootController
import ui.themes.components.ScreenHeader

@Composable
fun PredictionResultsScreen(recordId: Long) {
  val rootController = LocalRootController.current

  ViewModel(factory = { PredictionResultsViewModel(recordId) }) { viewModel ->
    val viewState by viewModel.viewStates().observeAsState()
    val viewAction by viewModel.viewActions().observeAsState()

    PredictionResultsView(
      state = viewState,
      onBackClick = { viewModel.obtainEvent(PredictionResultsViewEvent.OnBackClicked) }
    )

    when(viewAction) {
      PredictionResultsViewAction.NavigateToBack -> {
        viewModel.obtainEvent(PredictionResultsViewEvent.ActionInvoked)
        rootController.popBackStack()
      }
      null -> { /* ignore */ }
    }
  }
}

@Composable
private fun PredictionResultsView(state: PredictionResultsViewState, onBackClick: () -> Unit) {
  Column {
    ScreenHeader(
      modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp),
      title = "Определение типа упражнения",
      onBackClick = onBackClick,
      backEnabled = true
    )

    state.results.forEach {
      Text(it)
    }
  }
}