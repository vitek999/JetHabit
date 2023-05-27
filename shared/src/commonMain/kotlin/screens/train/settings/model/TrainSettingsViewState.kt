package screens.train.settings.model

data class TrainSettingsViewState(
    val trainTime: Long = 10,
    val errorText: String? = null,
)
