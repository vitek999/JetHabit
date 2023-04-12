package screens.users.detailed.model

import data.features.users.models.User
import utils.ProgressState

data class UserDetailedViewState(val progressState: ProgressState = ProgressState.Init, val user: User? = null) {

}
