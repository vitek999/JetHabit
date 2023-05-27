package screens.users.create.model

import data.features.users.models.Gender
import utils.ProgressState

data class CreateUserViewState(
    val progressState: ProgressState = ProgressState.Init,
    val firstName: String = "",
    val secondName: String = "",
    val age: Int = 0,
    val weight: Int = 0,
    val height: Int = 0,
    val gender: Gender = Gender.Male,
    val errorText: String? = null,
)