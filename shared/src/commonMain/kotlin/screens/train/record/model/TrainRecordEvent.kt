package screens.train.record.model

sealed class TrainRecordEvent {
    data class TrainRecordTimeChanged(val time: Long) : TrainRecordEvent()
    object ActionInvoked : TrainRecordEvent()
    object OnBackPressed : TrainRecordEvent()
    object OnSaveExerciseTimestampPressed : TrainRecordEvent()
    object OnStartRecordClick : TrainRecordEvent()
    object OnStopRecordClick : TrainRecordEvent()
}