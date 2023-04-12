package screens.users.main

import com.adeo.kviewmodel.BaseSharedViewModel
import data.features.users.UserRepository
import data.features.users.models.User
import di.Inject
import kotlinx.coroutines.launch
import screens.users.main.models.UsersMainAction
import screens.users.main.models.UsersMainEvent
import screens.users.main.models.UsersMainViewState
import utils.ProgressState

class UsersMainViewModel : BaseSharedViewModel<UsersMainViewState, UsersMainAction, UsersMainEvent>(
    initialState = UsersMainViewState()
) {
    private val usersRepository = Inject.instance<UserRepository>()

    init {
        fetchUsers()
    }

    override fun obtainEvent(viewEvent: UsersMainEvent) {
        when (viewEvent) {
            UsersMainEvent.ActionInvoked -> viewAction = null
            is UsersMainEvent.OnUserClicked -> handleOnUserClicked(viewEvent.user)
            is UsersMainEvent.OnUserSetSelected -> handleSelectedUser(viewEvent.user)
            UsersMainEvent.OnCreateUserClicked -> handleOnCreateUserClicked()
            UsersMainEvent.OnBackClicked -> handleBackClicked()
        }
    }

    private fun handleBackClicked() {
        viewAction = UsersMainAction.NavigateBack
    }

    private fun handleOnCreateUserClicked() {
        viewAction = UsersMainAction.OpenUserCreateScreen
    }

    private fun handleOnUserClicked(user: User) {
        viewAction = UsersMainAction.OpenDetailedUser(userId = user.id)
    }

    private fun handleSelectedUser(user: User) {
        viewModelScope.launch {
            runCatching {
                usersRepository.setSelectedUser(user)
                viewState = viewState.copy(selectedUser = user)
            }.onFailure { error ->
                error.printStackTrace()
            }
        }
    }

    private fun fetchUsers() {
        viewState = viewState.copy(progressState = ProgressState.Loading, users = emptyList())
        viewModelScope.launch {
            runCatching {
                val activeUser = usersRepository.getSelectedUser()
                val data = usersRepository.fetchAllUsers()
                viewState = viewState.copy(progressState = ProgressState.Loaded, users = data, selectedUser = activeUser)
            }.onFailure {
                viewState = viewState.copy(progressState = ProgressState.Error, users = emptyList())
            }
        }
    }
}