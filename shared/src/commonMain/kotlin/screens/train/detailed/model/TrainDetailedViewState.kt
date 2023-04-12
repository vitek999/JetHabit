package screens.train.detailed.model

import data.features.trains.main.models.Record
import data.features.trains.main.models.Train
import data.features.users.models.User
import utils.ProgressState

data class TrainDetailedViewState(
    val progressState: ProgressState = ProgressState.Init,
    val currentUser: User? = null,
    val train: Train? = null,
    val results: List<Record> = emptyList(),
)