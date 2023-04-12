package screens.users.main

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.adeo.kviewmodel.compose.ViewModel
import data.features.users.models.User
import navigation.NavigationTree
import ru.alexgladkov.odyssey.compose.extensions.push
import ru.alexgladkov.odyssey.compose.local.LocalRootController
import screens.users.main.models.UsersMainAction
import screens.users.main.models.UsersMainEvent
import screens.users.main.views.UsersMainView

@Composable
internal fun UsersMainScreen() {
    val rootController = LocalRootController.current

    ViewModel(factory = { UsersMainViewModel() }) { viewModel ->
        val viewState by viewModel.viewStates().collectAsState()
        val viewAction by viewModel.viewActions().collectAsState(null)

        UsersMainView(
            viewState = viewState,
            onUserClick = { viewModel.obtainEvent(UsersMainEvent.OnUserClicked(it)) },
            onUserSelectDefaultClick = { viewModel.obtainEvent(UsersMainEvent.OnUserSetSelected(it)) },
            onCreateUserClick = { viewModel.obtainEvent(UsersMainEvent.OnCreateUserClicked) },
            onBackClick = { viewModel.obtainEvent(UsersMainEvent.OnBackClicked) }
        )

        when (viewAction) {
            is UsersMainAction.OpenDetailedUser -> {

            }

            is UsersMainAction.OpenUserCreateScreen -> {
                viewModel.obtainEvent(UsersMainEvent.ActionInvoked)
                rootController.push(NavigationTree.Users.Create.name)
            }

            null -> { /* ignore */
            }

            UsersMainAction.NavigateBack -> {
                viewModel.obtainEvent(UsersMainEvent.ActionInvoked)
                rootController.popBackStack()
            }
        }
    }
}