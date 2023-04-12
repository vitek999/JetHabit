package screens.users.detailed.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.PointerIconDefaults.Text
import screens.users.detailed.model.UserDetailedViewState
import ui.themes.JetHabitTheme

@Composable
internal fun UserDetailedView(state: UserDetailedViewState) {
    Column(modifier = Modifier.fillMaxSize()) {
        Text(
            text = "Пользователь",
            style = JetHabitTheme.typography.heading,
            color = JetHabitTheme.colors.primaryText,
        )
    }
}