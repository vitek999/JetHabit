package screens.users.create

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import com.adeo.kviewmodel.compose.ViewModel
import com.adeo.kviewmodel.compose.observeAsState
import ru.alexgladkov.odyssey.compose.local.LocalRootController
import screens.users.create.model.CreateUserAction
import screens.users.create.model.CreateUserEvent
import screens.users.create.view.CreateUserView

@Composable
internal fun CreateUserScreen() {
    val rootController = LocalRootController.current

    ViewModel(factory = { CreateUserViewModel() }) { viewModel ->
        val viewState by viewModel.viewStates().observeAsState()
        val viewAction by viewModel.viewActions().observeAsState()

        CreateUserView(
            viewState = viewState,
            onFirstNameChange = { viewModel.obtainEvent(CreateUserEvent.FirstNameChanged(it)) },
            onSecondNameChange = { viewModel.obtainEvent(CreateUserEvent.SecondNameChanged(it)) },
            onAgeChange = { viewModel.obtainEvent(CreateUserEvent.AgeChanged(it)) },
            onGenderChange = { viewModel.obtainEvent(CreateUserEvent.GenderChanged(it)) },
            onWeightChange = { viewModel.obtainEvent(CreateUserEvent.WeightChanged(it)) },
            onHeightChange = { viewModel.obtainEvent(CreateUserEvent.HeightChanged(it)) },
            onSaveItemClick = { viewModel.obtainEvent(CreateUserEvent.OnSaveClicked) },
            onBackClick = { viewModel.obtainEvent(CreateUserEvent.OnBackClicked) }
        )

        when (viewAction) {
            CreateUserAction.UserSaved -> {
                viewModel.obtainEvent(CreateUserEvent.ActionInvoked)
                rootController.popBackStack()
            }
            CreateUserAction.NavigateBack -> {
                viewModel.obtainEvent(CreateUserEvent.ActionInvoked)
                rootController.popBackStack()
            }
            null -> { /* ignore */}
        }
    }
}