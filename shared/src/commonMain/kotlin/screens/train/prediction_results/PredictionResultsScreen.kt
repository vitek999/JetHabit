package screens.train.prediction_results

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.adeo.kviewmodel.compose.ViewModel
import com.adeo.kviewmodel.compose.observeAsState
import ru.alexgladkov.odyssey.compose.local.LocalRootController
import ui.themes.components.ScreenHeader
import utils.SnackbarView

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
  Box(modifier = Modifier.fillMaxSize()) {
    Column {
      ScreenHeader(
        modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp),
        title = "Определение типа упражнения",
        onBackClick = onBackClick,
        backEnabled = true
      )

      if (state.results.isEmpty()) {
        Text(
          modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp),
          textAlign = TextAlign.Center,
          text = "ошибка получения данных о типе упражнения",
          color = Color.Red,
        )
      } else {
        state.results.forEach {
          PredictionResiltsItem(it)
        }
      }

    }

    SnackbarView(text = state.errorText)
  }
}

@Composable
private fun PredictionResiltsItem(text: String) {
  Box(
    modifier = Modifier.fillMaxWidth()
      .padding(start = 8.dp, top = 8.dp, end = 8.dp)
      .clip(RoundedCornerShape(8.dp))
      .background(Color.White)
  ) {
    Text(modifier = Modifier.fillMaxWidth().padding(8.dp), text = text)
  }
}