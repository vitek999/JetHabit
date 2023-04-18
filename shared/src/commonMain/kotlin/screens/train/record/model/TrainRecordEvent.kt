package screens.train.record.model

sealed class TrainRecordEvent {
    data class TrainRecordTimeChanged(val time: Int) : TrainRecordEvent()
    object ActionInvoked : TrainRecordEvent()
    object OnBackPressed : TrainRecordEvent()

    object OnStartRecordClick : TrainRecordEvent()
    object OnStopRecordClick : TrainRecordEvent()
}