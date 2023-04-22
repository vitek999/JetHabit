package screens.train.record

import com.adeo.kviewmodel.BaseSharedViewModel
import data.features.trains.main.TrainRepository
import data.features.trains.settings.SettingsRepository
import data.features.users.UserRepository
import di.Inject
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import screens.train.record.model.TrainRecordAction
import screens.train.record.model.TrainRecordEvent
import screens.train.record.model.TrainRecordViewState
import sensors.CustomSensorManager
import sensors.SensorData
import utils.SoundManager
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
    private val settingsRepository = Inject.instance<SettingsRepository>()
    private val soundManager = Inject.instance<SoundManager>()

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
                    trainRepository.addRecord(userId, trainId, data, viewState.timestamps, viewState.recordTime)
                    soundManager.play()
                    println("Показания успешно сохранены")
                }

            }
        })

        val defaultRecordTime = settingsRepository.getDefaultTrainTime()
        viewState = viewState.copy(recordTime = defaultRecordTime)
    }

    override fun obtainEvent(viewEvent: TrainRecordEvent) {
        when(viewEvent) {
            TrainRecordEvent.ActionInvoked -> viewAction = null
            TrainRecordEvent.OnBackPressed -> handleOnBackPressed()
            TrainRecordEvent.OnStartRecordClick -> handleOnStartRecordClick()
            TrainRecordEvent.OnStopRecordClick -> handleOnStopRecordClick()
            is TrainRecordEvent.TrainRecordTimeChanged -> handleOnTrainRecordTimeChanged(viewEvent.time)
            TrainRecordEvent.OnSaveExerciseTimestampPressed -> handleOnSaveExerciseTimestampPressed()
        }
    }

    private fun handleOnSaveExerciseTimestampPressed() {
        if (viewState.recording) {
            val timestamp = (Clock.System.now().toEpochMilliseconds() - viewState.startRecordTimeStamp) * 1_000_000
            viewState = viewState.copy(timestamps = viewState.timestamps + timestamp)
            println("TEST: $timestamp")
        }
    }

    private fun handleOnTrainRecordTimeChanged(time: Long) {
        viewState = viewState.copy(recordTime = time)
    }

    private fun handleOnStopRecordClick() {
        sensorManager.stop()
        viewState = viewState.copy(recording = false, timestamps = emptyList(), startRecordTimeStamp = 0L)
    }

    private fun handleOnStartRecordClick() {
        soundManager.play()
        sensorManager.start(viewState.recordTime.toDuration(DurationUnit.SECONDS))
        viewState = viewState.copy(recording = true, timestamps = emptyList(), startRecordTimeStamp = Clock.System.now().toEpochMilliseconds())
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