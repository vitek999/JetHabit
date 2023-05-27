package screens.train.settings

import com.adeo.kviewmodel.BaseSharedViewModel
import data.features.trains.settings.SettingsRepository
import data.features.users.UserRepository
import data.features.users.models.User
import data.features.users.models.UserDto
import data.features.users.models.asDto
import di.Inject
import di.PlatformConfiguration
import kotlinx.coroutines.*
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.json.Json
import screens.train.settings.model.TrainSettingsAction
import screens.train.settings.model.TrainSettingsEvent
import screens.train.settings.model.TrainSettingsViewState

class TrainSettingsViewModel : BaseSharedViewModel<TrainSettingsViewState, TrainSettingsAction, TrainSettingsEvent>(
    initialState = TrainSettingsViewState()
) {
    private val settingsRepository = Inject.instance<SettingsRepository>()
    private val platformConfiguration = Inject.instance<PlatformConfiguration>()
    private val usersRepository = Inject.instance<UserRepository>()

    init {
        val savedTime = settingsRepository.getDefaultTrainTime()
        viewState = viewState.copy(trainTime = savedTime)
    }

    override fun obtainEvent(viewEvent: TrainSettingsEvent) {
        when (viewEvent) {
            TrainSettingsEvent.ActionInvoked -> viewAction = null
            TrainSettingsEvent.OnBackClicked -> handleOnBackClicked()
            TrainSettingsEvent.OnSaveClicked -> handleOnSaveClicked()
            is TrainSettingsEvent.TrainTimeChanged -> handleTrainTimeChange(viewEvent.time)
            TrainSettingsEvent.OnExportUserDataClicked -> handleExportUserDataClicked()
        }
    }

    private fun handleExportUserDataClicked() {
        viewModelScope.launch {
            val users = usersRepository.fetchAllUsers().map(User::asDto)
            val json = Json.encodeToString(ListSerializer(UserDto.serializer()), users)
            platformConfiguration.exportDataToFile(json, "users.json")
        }

    }

    private fun handleOnBackClicked() {
        viewAction = TrainSettingsAction.NavigateBack
    }

    private fun handleOnSaveClicked() {
        if (viewState.trainTime >= 10) {
            settingsRepository.saveDefaultTrainTime(viewState.trainTime)
            viewAction = TrainSettingsAction.NavigateBack
        } else {
            showErrorMessage("длительность упраженния должна быть 10 секунд и больше")
        }
    }

    private fun handleTrainTimeChange(time: Long) {
        viewState = viewState.copy(trainTime = time)
    }

    private fun showErrorMessage(text: String) {
        viewModelScope.launch {
            viewState = viewState.copy(errorText = text)
            delay(ERROR_MESSAGE_TIME_IN_MILLS)
            viewState = viewState.copy(errorText = null)
        }
    }

    companion object {
        private const val ERROR_MESSAGE_TIME_IN_MILLS = 3000L
    }
}