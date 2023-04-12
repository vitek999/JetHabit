package screens.users.detailed

import com.adeo.kviewmodel.BaseSharedViewModel
import data.features.users.UserRepository
import di.Inject
import kotlinx.coroutines.launch
import screens.users.detailed.model.UserDetailedAction
import screens.users.detailed.model.UserDetailedEvent
import screens.users.detailed.model.UserDetailedViewState
import utils.ProgressState

class UserDetailedViewModel(userId: Long) : BaseSharedViewModel<UserDetailedViewState, UserDetailedAction, UserDetailedEvent>(
    initialState = UserDetailedViewState()
) {
    private val userRepository = Inject.instance<UserRepository>()

    init {
        fetchUser(userId)
    }

    override fun obtainEvent(viewEvent: UserDetailedEvent) {
        when(viewEvent) {
            UserDetailedEvent.ActionInvoked -> viewAction = null
        }
    }

    private fun fetchUser(userId: Long) {
        viewState = viewState.copy(progressState = ProgressState.Loading, user = null)
        viewModelScope.launch {
            runCatching {
                val data = userRepository.fetchUserById(userId)
                viewState = viewState.copy(progressState = ProgressState.Loaded, user = data)
            }.onFailure {
                viewState = viewState.copy(progressState = ProgressState.Error, user = null)
            }
        }
    }
}