package screens.train.main.view

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import data.features.trains.main.models.Train
import screens.train.main.model.TrainMainScreenViewState
import tech.mobiledeveloper.shared.AppRes
import ui.themes.JetHabitTheme
import ui.themes.components.JetHabitButton
import ui.themes.components.ScreenHeader

@Composable
internal fun TrainMainView(
    viewState: TrainMainScreenViewState,
    onRowClick: (train: Train) -> Unit,
    onUsersClick: () -> Unit,
    onBackClick: () -> Unit,
    onSettingsClick: () -> Unit,
) {
    Column(
        modifier = Modifier.fillMaxSize()
            .background(JetHabitTheme.colors.primaryBackground)
    ) {

        ScreenHeader(
            title = AppRes.string.trains,
            onBackClick = onBackClick,
            backEnabled = false,
        )

        Row(
            modifier = Modifier.fillMaxWidth().padding(top = 8.dp, end = 8.dp, start = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            JetHabitButton(
                text = AppRes.string.users,
                onClick = onUsersClick,
            )

            val selectedUser = viewState.selectedUser
            if (selectedUser != null) {
                Text(
                    modifier = Modifier.padding(start = 8.dp),
                    text = "Выбранный пользователь: ${selectedUser.lastName} ${selectedUser.firstName}",
                    style = JetHabitTheme.typography.body,
                    color = JetHabitTheme.colors.primaryText,
                )
            } else {
                Text(
                    modifier = Modifier.padding(start = 8.dp),
                    text = "Активный пользователь не выбран",
                    style = JetHabitTheme.typography.body,
                    color = JetHabitTheme.colors.primaryText,
                )
            }
        }

        JetHabitButton(
            modifier = Modifier.fillMaxWidth().padding(start = 8.dp, end = 8.dp, top = 8.dp),
            text  = AppRes.string.settings_button_text,
            onClick = onSettingsClick,
        )


        if (viewState.selectedUser != null) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 16.dp, start = 8.dp, end = 8.dp), verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(viewState.trains) { trainItem ->
                    TrainRow(train = trainItem, onRowClick = { onRowClick(trainItem) })
                }
            }
        } else {
            Text(
                modifier = Modifier.fillMaxWidth().padding(start = 8.dp, end = 8.dp, top = 8.dp),
                text = "Необходимо выбрать активного пользователя",
                style = JetHabitTheme.typography.body,
                color = JetHabitTheme.colors.primaryText,
                textAlign = TextAlign.Center,
            )
        }
    }
}

@Composable
private fun TrainRow(
    modifier: Modifier = Modifier, train: Train,
    onRowClick: () -> Unit,
) {
    Box(modifier = modifier
        .fillMaxWidth()
        .clip(RoundedCornerShape(8.dp))
        .background(JetHabitTheme.colors.secondaryBackground)
        .padding(8.dp)
        .clickable(onClick = onRowClick)
    ) {
        Text(
            text = train.title,
            style = JetHabitTheme.typography.body,
            color = JetHabitTheme.colors.primaryText,
        )
    }
}