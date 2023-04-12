package screens.train.record.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import screens.train.record.model.TrainRecordViewState
import ui.themes.JetHabitTheme
import ui.themes.components.JetHabitButton
import ui.themes.components.ScreenHeader

@Composable
fun TrainRecordView(
    viewState: TrainRecordViewState,
    onBackClicked: () -> Unit,
    onStarClick: () -> Unit,
    onStopClick: () -> Unit,
) {
    Column(modifier = Modifier.fillMaxSize()) {
        ScreenHeader(
            title = "Запись: ${viewState.train?.title.orEmpty()}",
            backEnabled = true,
            onBackClick = onBackClicked,
        )

        JetHabitButton(
            modifier = Modifier.fillMaxWidth().padding(top = 8.dp, start = 16.dp, end = 16.dp),
            text = "Начать",
            onClick = onStarClick,
        )

        JetHabitButton(
            modifier = Modifier.fillMaxWidth().padding(top = 8.dp, start = 16.dp, end = 16.dp),
            text = "Остановить",
            onClick = onStopClick,
        )

        if (viewState.recording) {
            Text(
                modifier = Modifier.padding(start = 16.dp, top = 8.dp),
                text = "Идет запись",
                style = JetHabitTheme.typography.body,
                color = JetHabitTheme.colors.primaryText,
            )
        }
    }
}