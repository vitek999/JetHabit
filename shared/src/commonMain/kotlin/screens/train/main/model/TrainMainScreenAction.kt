package screens.train.main.model

sealed class TrainMainScreenAction {
    data class NavigateToDetailTrain(val id: Long): TrainMainScreenAction()
    object OpenUsersScreen : TrainMainScreenAction()
    object NavigateBack : TrainMainScreenAction()
}