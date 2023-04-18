package screens.train.settings

import com.adeo.kviewmodel.BaseSharedViewModel
import data.features.trains.settings.SettingsRepository
import di.Inject
import screens.train.settings.model.TrainSettingsAction
import screens.train.settings.model.TrainSettingsEvent
import screens.train.settings.model.TrainSettingsViewState

class TrainSettingsViewModel : BaseSharedViewModel<TrainSettingsViewState, TrainSettingsAction, TrainSettingsEvent>(
    initialState = TrainSettingsViewState()
) {
    private val settingsRepository = Inject.instance<SettingsRepository>()

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
        }
    }

    private fun handleOnBackClicked() {
        viewAction = TrainSettingsAction.NavigateBack
    }

    private fun handleOnSaveClicked() {
        settingsRepository.saveDefaultTrainTime(viewState.trainTime)
        viewAction = TrainSettingsAction.NavigateBack
    }

    private fun handleTrainTimeChange(time: Int) {
        viewState = viewState.copy(trainTime = time)
    }
}