package screens.users.detailed

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import com.adeo.kviewmodel.compose.ViewModel
import com.adeo.kviewmodel.compose.observeAsState
import screens.users.detailed.view.UserDetailedView

@Composable
internal fun UserDetailedScreen(userId: Long) {
    ViewModel(factory = { UserDetailedViewModel(userId) }) {viewModel ->
        val viewState by viewModel.viewStates().observeAsState()
        val viewAction by viewModel.viewActions().observeAsState()

        UserDetailedView(viewState)
    }
}