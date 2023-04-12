package screens.users.main.models

import data.features.users.models.User
import utils.ProgressState

data class UsersMainViewState(
    val progressState: ProgressState = ProgressState.Init,
    val users: List<User> = emptyList(),
    val selectedUser: User? = null,
)
