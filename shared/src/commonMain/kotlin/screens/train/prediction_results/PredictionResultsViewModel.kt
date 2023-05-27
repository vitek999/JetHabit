package screens.train.prediction_results

import com.adeo.kviewmodel.BaseSharedViewModel
import data.features.trains.main.TrainRepository
import data.features.trains.main.models.asDto
import di.Inject
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import screens.train.settings.TrainSettingsViewModel
import sensors.asDto

data class PredictionResultsViewState(
  val results: List<String> = emptyList(),
  val errorText: String? = null
)

sealed class PredictionResultsViewEvent {
  object OnBackClicked: PredictionResultsViewEvent()
  object ActionInvoked: PredictionResultsViewEvent()
}

sealed class PredictionResultsViewAction {
  object NavigateToBack: PredictionResultsViewAction()
}

class PredictionResultsViewModel(private val recordId: Long) :
  BaseSharedViewModel<PredictionResultsViewState, PredictionResultsViewAction, PredictionResultsViewEvent>(PredictionResultsViewState()) {
  private val trainRepository = Inject.instance<TrainRepository>()

  init {
    viewModelScope.launch {
      fetchPrediction(recordId)
    }
  }

  override fun obtainEvent(viewEvent: PredictionResultsViewEvent) {
    when(viewEvent) {
      PredictionResultsViewEvent.ActionInvoked -> viewAction = null
      PredictionResultsViewEvent.OnBackClicked -> {
        viewAction = PredictionResultsViewAction.NavigateToBack
      }
    }
  }

  private suspend fun fetchPrediction(recordId: Long) {
    runCatching {
      val record = trainRepository.fetchAllRecords().firstOrNull { it.id == recordId } ?: return
      val data = trainRepository.fetchSensorDataByTrain(recordId)
      val timestamps = trainRepository.fetchExercisesByRecordId(recordId)
      val recordDto = record.asDto(data.asDto(), timestamps)
      val results = trainRepository.predictResults(recordDto)
      viewState = viewState.copy(results = results)
      if (results.isEmpty()) {
        showErrorMessage("ошибка получения данных о типе упражнения")
      }
    }.getOrElse {
      it.printStackTrace()
    }
  }

  private fun showErrorMessage(text: String) {
    viewModelScope.launch {
      viewState = viewState.copy(errorText = text)
      delay(ERROR_MESSAGE_TIME_IN_MILLS)
      viewState = viewState.copy(errorText = null)
    }
  }

  companion object {
    private const val ERROR_MESSAGE_TIME_IN_MILLS = 3000L
  }
}
