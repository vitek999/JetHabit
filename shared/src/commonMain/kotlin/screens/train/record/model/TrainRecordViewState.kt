package screens.train.record.model

import data.features.trains.main.models.Train
import data.features.users.models.User

data class TrainRecordViewState(
    val recording: Boolean = false,
    val user: User? = null,
    val train: Train? = null,
    val recordTime: Int = 20,
)