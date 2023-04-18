package screens.train.main.model

import data.features.trains.main.models.Train

sealed class TrainMainScreenEvent {
    data class TrainSelected(val train: Train) : TrainMainScreenEvent()
    object OnUsersClicked : TrainMainScreenEvent()
    object ActionInvoked : TrainMainScreenEvent()
    object OnBackClicked : TrainMainScreenEvent()
    object OnSettingsClicked : TrainMainScreenEvent()
}