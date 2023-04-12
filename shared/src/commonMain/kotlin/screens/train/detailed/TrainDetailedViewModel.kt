package screens.train.detailed

import com.adeo.kviewmodel.BaseSharedViewModel
import data.features.trains.main.TrainRepository
import data.features.users.UserRepository
import di.Inject
import kotlinx.coroutines.launch
import screens.train.detailed.model.TrainDetailedAction
import screens.train.detailed.model.TrainDetailedEvent
import screens.train.detailed.model.TrainDetailedViewState
import sensors.CustomSensorManager
import sensors.SensorData
import utils.ProgressState
import kotlin.time.DurationUnit
import kotlin.time.toDuration

class TrainDetailedViewModel(private val trainId: Long) : BaseSharedViewModel<TrainDetailedViewState, TrainDetailedAction, TrainDetailedEvent>(
    initialState = TrainDetailedViewState()
) {
    private val trainRepository = Inject.instance<TrainRepository>()
    private val userRepository = Inject.instance<UserRepository>()


    init {
        fetchTrain(trainId)
        fetchCurrentUser()
        fetchRecords()
//        sensorManager.start(10.toDuration(DurationUnit.SECONDS))
//        sensorManager.setListener(object : CustomSensorManager.CustomSensorListener {
//            override fun newValue(data: SensorData) {
//                //save
//                println("SAVED DATA: ${data.accelerometerValue.size}")
//                println("SAVED DATA: ${data.gyroscopeValue.size}")
//
//                println("Показания успешно сохранены")
//            }
//        })
    }

    override fun obtainEvent(viewEvent: TrainDetailedEvent) {
        when(viewEvent) {
            TrainDetailedEvent.ActionInvoked -> viewAction = null
            TrainDetailedEvent.OnBackClicked -> handleBackClick()
            TrainDetailedEvent.OnRecordClicked -> handleOnRecordClicked()
            is TrainDetailedEvent.OnSavedRecordClicked -> handleOnSavedRecordClicked(recordId = viewEvent.recordId)
        }
    }

    private fun handleOnSavedRecordClicked(recordId: Long) {
        viewModelScope.launch {
            val data = trainRepository.fetchSensorDataByTrain(recordId)
            println(data)
            println("${data.gyroscopeValue.size} ${data.accelerometerValue.size}")
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