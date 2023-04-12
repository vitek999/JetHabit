package screens.train.detailed.model

sealed class TrainDetailedEvent {
    object ActionInvoked : TrainDetailedEvent()
    object OnBackClicked : TrainDetailedEvent()
    object OnRecordClicked : TrainDetailedEvent()

    data class OnSavedRecordClicked(val recordId: Long) : TrainDetailedEvent()
}