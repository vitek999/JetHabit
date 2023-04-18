package screens.train.settings.model

sealed class TrainSettingsEvent {
    data class TrainTimeChanged(val time: Int) : TrainSettingsEvent()
    object ActionInvoked : TrainSettingsEvent()
    object OnBackClicked : TrainSettingsEvent()
    object OnSaveClicked : TrainSettingsEvent()
}