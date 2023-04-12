package screens.train.record.model

sealed class TrainRecordEvent {
    object ActionInvoked : TrainRecordEvent()
    object OnBackPressed : TrainRecordEvent()

    object OnStartRecordClick : TrainRecordEvent()
    object OnStopRecordClick : TrainRecordEvent()
}