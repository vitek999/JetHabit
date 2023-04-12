package screens.train.main.model

import data.features.trains.main.models.Train
import data.features.users.models.User
import utils.ProgressState

data class TrainMainScreenViewState(
    val state: ProgressState = ProgressState.Init,
    val trains: List<Train> = emptyList(),
    val selectedUser: User? = null,
)
