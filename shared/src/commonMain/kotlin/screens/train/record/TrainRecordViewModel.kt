package screens.train.record

import com.adeo.kviewmodel.BaseSharedViewModel
import data.features.trains.main.TrainRepository
import data.features.users.UserRepository
import di.Inject
import kotlinx.coroutines.launch
import screens.train.record.model.TrainRecordAction
import screens.train.record.model.TrainRecordEvent
import screens.train.record.model.TrainRecordViewState
import sensors.CustomSensorManager
import sensors.SensorData
import kotlin.time.DurationUnit
import kotlin.time.toDuration

class TrainRecordViewModel(
     private val userId: Long,
     private val trainId: Long
): BaseSharedViewModel<TrainRecordViewState, TrainRecordAction, TrainRecordEvent>(
    initialState = TrainRecordViewState()
) {
    private val userRepository = Inject.instance<UserRepository>()
    private val trainRepository = Inject.instance<TrainRepository>()
    private val sensorManager = Inject.instance<CustomSensorManager>()

    init {
        fetchUser(userId)
        fetchTrain(trainId)
        sensorManager.setListener(object : CustomSensorManager.CustomSensorListener {
            override fun newValue(data: SensorData) {
                //save
                println(data)
                println("SAVED DATA: ${data.accelerometerValue.size}")
                println("SAVED DATA: ${data.gyroscopeValue.size}")

                viewModelScope.launch {
                    viewState = viewState.copy(recording = false)
                    trainRepository.addRecord(userId, trainId, data)
                    println("Показания успешно сохранены")
                }

            }
        })
    }

    override fun obtainEvent(viewEvent: TrainRecordEvent) {
        when(viewEvent) {
            TrainRecordEvent.ActionInvoked -> viewAction = null
            TrainRecordEvent.OnBackPressed -> handleOnBackPressed()
            TrainRecordEvent.OnStartRecordClick -> handleOnStartRecordClick()
            TrainRecordEvent.OnStopRecordClick -> handleOnStopRecordClick()
        }
    }

    private fun handleOnStopRecordClick() {
        sensorManager.stop()
        viewState = viewState.copy(recording = false)
    }

    private fun handleOnStartRecordClick() {
        sensorManager.start(10.toDuration(DurationUnit.SECONDS))
        viewState = viewState.copy(recording = true)
    }

    private fun handleOnBackPressed() {
        viewAction = TrainRecordAction.NavigateBack
    }

    private fun fetchUser(userId: Long) {
        viewModelScope.launch {
            val user = userRepository.fetchUserById(userId)
            viewState = viewState.copy(user = user)
        }
    }

    private fun fetchTrain(trainId: Long) {
        viewModelScope.launch {
            val train = trainRepository.fetchTrainById(trainId)
            viewState = viewState.copy(train = train)
        }
    }
}