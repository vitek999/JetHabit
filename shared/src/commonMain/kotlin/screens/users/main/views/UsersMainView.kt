package screens.users.main.views

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import data.features.users.models.User
import screens.detail.models.DetailEvent
import screens.users.main.models.UsersMainViewState
import tech.mobiledeveloper.shared.AppRes
import ui.themes.JetHabitTheme
import ui.themes.components.JetHabitButton
import ui.themes.components.ScreenHeader

@Composable
internal fun UsersMainView(
    viewState: UsersMainViewState,
    onUserClick: (User) -> Unit,
    onUserSelectDefaultClick: (User) -> Unit,
    onCreateUserClick: () -> Unit,
    onBackClick: () -> Unit,
) {
    Column(modifier = Modifier.fillMaxSize()) {
        ScreenHeader(title = AppRes.string.users, onBackClick = onBackClick)

        JetHabitButton(
            modifier = Modifier.padding(top = 8.dp, start = 8.dp),
            text = AppRes.string.create_new_user_button_text,
            onClick = onCreateUserClick,
        )
        LazyColumn(modifier = Modifier.fillMaxSize().padding(top = 16.dp)) {
            items(viewState.users) { user ->
                UserRow(
                    user = user,
                    isSelected = viewState.selectedUser == user,
                    onRowClick = { onUserClick(user) },
                    onSetSelectedClick = { onUserSelectDefaultClick(user) }
                )
            }
        }
    }
}

@Composable
private fun UserRow(
    user: User,
    isSelected: Boolean,
    onRowClick: () -> Unit,
    onSetSelectedClick: () -> Unit,
) {
    Row(
        modifier = Modifier.fillMaxWidth()
            .padding(top = 8.dp, start = 8.dp, end = 8.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(JetHabitTheme.colors.secondaryBackground)
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = user.firstName,
                style = JetHabitTheme.typography.body,
                color = JetHabitTheme.colors.primaryText,
            )

            Text(
                modifier = Modifier.padding(top = 4.dp),
                text = user.lastName,
                style = JetHabitTheme.typography.body,
                color = JetHabitTheme.colors.primaryText,
            )
        }

        JetHabitButton(
            enabled = !isSelected,
            text = if (isSelected) AppRes.string.selected_button_text else AppRes.string.select_current_user_button_text,
            onClick = onSetSelectedClick,
        )
    }
}