package screens.train.detailed

import com.adeo.kviewmodel.BaseSharedViewModel
import data.features.trains.main.TrainRepository
import data.features.trains.main.models.RecordDto
import data.features.trains.main.models.asDto
import data.features.users.UserRepository
import di.Inject
import di.PlatformConfiguration
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.json.Json
import screens.train.detailed.model.TrainDetailedAction
import screens.train.detailed.model.TrainDetailedEvent
import screens.train.detailed.model.TrainDetailedViewState
import sensors.asDto
import utils.ProgressState

class TrainDetailedViewModel(private val trainId: Long) : BaseSharedViewModel<TrainDetailedViewState, TrainDetailedAction, TrainDetailedEvent>(
    initialState = TrainDetailedViewState()
) {
    private val trainRepository = Inject.instance<TrainRepository>()
    private val userRepository = Inject.instance<UserRepository>()
    private val platformConfiguration = Inject.instance<PlatformConfiguration>()


    init {
        fetchTrain(trainId)
        fetchCurrentUser()
        fetchRecords()
    }

    override fun obtainEvent(viewEvent: TrainDetailedEvent) {
        when(viewEvent) {
            TrainDetailedEvent.ActionInvoked -> viewAction = null
            TrainDetailedEvent.OnBackClicked -> handleBackClick()
            TrainDetailedEvent.OnRecordClicked -> handleOnRecordClicked()
            is TrainDetailedEvent.OnSavedRecordClicked -> handleOnSavedRecordClicked(recordId = viewEvent.recordId)
            TrainDetailedEvent.OnExportClicked -> handleOnExportClicked()
            is TrainDetailedEvent.OnDeleteClicked -> handleOnDeleteClicked(viewEvent.recordId)
        }
    }

    private fun handleOnDeleteClicked(recordId: Long) {
        viewModelScope.launch {
            trainRepository.deleteRecord(recordId)
            fetchRecords()
        }
    }

    private fun handleOnExportClicked() {
        viewModelScope.launch {
            val recordsDto = viewState.results.map {
                val data = trainRepository.fetchSensorDataByTrain(it.id).asDto()
                val timestamps = trainRepository.fetchExercisesByRecordId(it.id)
                it.asDto(data, timestamps)
            }
            println("TEST: ${recordsDto.last().timestamps}")
            val json = Json.encodeToString(ListSerializer(RecordDto.serializer()), recordsDto)
            val fileName = "${viewState.currentUser?.id}_${viewState.train?.id}_${Clock.System.now()}.json"
            platformConfiguration.exportDataToFile(json, fileName)
        }
    }

    private fun handleOnSavedRecordClicked(recordId: Long) {
        viewModelScope.launch {
            val record = viewState.results.firstOrNull { it.id == recordId } ?: return@launch
//            if (record.trainId == TrainRepository.DETECT_TRAIN_ID) {
                val data = trainRepository.fetchSensorDataByTrain(recordId)
                println("${data.gyroscopeValue.size} ${data.accelerometerValue.size}")
                viewAction = TrainDetailedAction.OpenRecordPrediction(recordId)
//            }
        }
    }

    private fun handleOnRecordClicked() {
        val trainId = viewState.train?.id ?: error("train not defined")
        val userId = viewState.currentUser?.id ?: error("user not defined")
        viewAction = TrainDetailedAction.NavigateRecord(trainId = trainId, userId = userId)
    }

    private fun fetchRecords() {
        viewModelScope.launch {
            val userId = userRepository.getSelectedUser()?.id ?: error("user not defined")
            val records = trainRepository.fetchByTrainIdAndUserId(
                userId = userId,
                trainId = trainId,
            )
            viewState = viewState.copy(results = records)
        }
    }

    private fun fetchTrain(trainId: Long) {
        viewState = viewState.copy(progressState = ProgressState.Loading)
        viewModelScope.launch {
            runCatching {
                val data = trainRepository.fetchTrainById(trainId)
                viewState = viewState.copy(progressState = ProgressState.Loaded, train = data)
            }.onFailure {
                viewState = viewState.copy(progressState = ProgressState.Error, train = null)
            }
        }
    }

    private fun fetchCurrentUser() {
        viewModelScope.launch {
            val currentUser = userRepository.getSelectedUser()
            viewState = viewState.copy(currentUser = currentUser)
        }
    }

    private fun handleBackClick() {
        viewAction = TrainDetailedAction.NavigateBack
    }
}