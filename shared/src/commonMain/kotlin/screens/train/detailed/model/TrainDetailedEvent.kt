package screens.train.detailed.model

sealed class TrainDetailedEvent {
    object ActionInvoked : TrainDetailedEvent()
    object OnBackClicked : TrainDetailedEvent()
    object OnRecordClicked : TrainDetailedEvent()
    object OnExportClicked : TrainDetailedEvent()

    data class OnSavedRecordClicked(val recordId: Long) : TrainDetailedEvent()
    data class OnDeleteClicked(val recordId: Long) : TrainDetailedEvent()
}