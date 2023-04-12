package screens.splash

import com.adeo.kviewmodel.BaseSharedViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

sealed class SplashScreenAction {
    object NavigateToMainMenuScreen : SplashScreenAction()
}

sealed class SplashScreenEvent {
    object SplashScreenDisplayed : SplashScreenEvent()
    object ActionInvoked : SplashScreenEvent()
}

class SplashScreenViewModel: BaseSharedViewModel<Any, SplashScreenAction, SplashScreenEvent>(Any()) {
    override fun obtainEvent(viewEvent: SplashScreenEvent) {
        when(viewEvent) {
            SplashScreenEvent.SplashScreenDisplayed -> handleSplashScreenDisplayed()
            SplashScreenEvent.ActionInvoked -> viewAction = null
        }
    }

    private fun handleSplashScreenDisplayed()  {
        viewModelScope.launch {
            delay(SPLASH_SCREEN_DELAY)
            viewAction = SplashScreenAction.NavigateToMainMenuScreen
        }
    }

    companion object {
        private const val SPLASH_SCREEN_DELAY: Long = 1000L
    }
}