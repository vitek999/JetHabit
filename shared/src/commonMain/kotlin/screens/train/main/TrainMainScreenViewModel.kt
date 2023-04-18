package screens.train.main

import com.adeo.kviewmodel.BaseSharedViewModel
import data.features.trains.main.TrainRepository
import data.features.trains.main.models.Train
import data.features.users.UserRepository
import di.Inject
import kotlinx.coroutines.launch
import screens.train.main.model.TrainMainScreenAction
import screens.train.main.model.TrainMainScreenEvent
import screens.train.main.model.TrainMainScreenViewState
import utils.ProgressState

class TrainMainScreenViewModel :
    BaseSharedViewModel<TrainMainScreenViewState, TrainMainScreenAction, TrainMainScreenEvent>(
        initialState = TrainMainScreenViewState()
    ) {

    private val trainRepository = Inject.instance<TrainRepository>()
    private val userRepository = Inject.instance<UserRepository>()

    init {
        fetchTrains()
        fetchSelectedUser()
    }

    override fun obtainEvent(viewEvent: TrainMainScreenEvent) {
        when (viewEvent) {
            TrainMainScreenEvent.ActionInvoked -> viewAction = null
            is TrainMainScreenEvent.TrainSelected -> handleTrainSelectedEvent(viewEvent.train)
            TrainMainScreenEvent.OnUsersClicked -> handleOnUsersClicked()
            TrainMainScreenEvent.OnBackClicked -> handleOnBackClicked()
            TrainMainScreenEvent.OnSettingsClicked -> handleOnSettingsClicked()
        }
    }

    private fun handleOnSettingsClicked() {
        viewAction = TrainMainScreenAction.OpenSettings
    }

    private fun handleOnBackClicked() {
        viewAction = TrainMainScreenAction.NavigateBack
    }

    private fun handleOnUsersClicked() {
        viewAction = TrainMainScreenAction.OpenUsersScreen
    }

    private fun fetchSelectedUser() {
        viewModelScope.launch {
            runCatching {
                val data = userRepository.getSelectedUser() ?: run {
                    val defaultUser = userRepository.fetchAllUsers().firstOrNull()
                    userRepository.setSelectedUser(defaultUser)
                    defaultUser
                }
                viewState = viewState.copy(selectedUser = data)
            }.onFailure {
                viewState = viewState.copy(selectedUser = null)
            }
        }
    }

    private fun fetchTrains() {
        viewState = viewState.copy(state = ProgressState.Loading, trains = emptyList())
        viewModelScope.launch {
            val data = trainRepository.fetchAllTrains()
            runCatching {
                viewState = viewState.copy(state = ProgressState.Loaded, trains = data)
            }.onFailure {
                viewState = viewState.copy(state = ProgressState.Error, trains = emptyList())
            }
        }
    }

    private fun handleTrainSelectedEvent(train: Train) {
        viewAction = TrainMainScreenAction.NavigateToDetailTrain(train.id)
        println(train)
    }
}