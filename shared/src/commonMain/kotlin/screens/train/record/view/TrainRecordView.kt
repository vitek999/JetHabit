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
import tech.mobiledeveloper.shared.AppRes
import ui.themes.JetHabitTheme
import ui.themes.components.IntegerField
import ui.themes.components.JetHabitButton
import ui.themes.components.ScreenHeader

@Composable
fun TrainRecordView(
    viewState: TrainRecordViewState,
    onBackClicked: () -> Unit,
    onStarClick: () -> Unit,
    onStopClick: () -> Unit,
    onRecordTimeChanged: (Int) -> Unit,
) {
    Column(modifier = Modifier.fillMaxSize()) {
        ScreenHeader(
            title = AppRes.string.train_title_text.format(viewState.train?.title.orEmpty()),
            backEnabled = true,
            onBackClick = onBackClicked,
        )

        JetHabitButton(
            modifier = Modifier.fillMaxWidth().padding(top = 8.dp, start = 16.dp, end = 16.dp),
            text = if (viewState.recording) AppRes.string.stop_button_text else AppRes.string.start_button_text,
            onClick = if (viewState.recording) onStopClick else onStarClick,
        )

        IntegerField(
            modifier = Modifier.fillMaxWidth().padding(top = 8.dp, start = 16.dp, end = 16.dp),
            value = viewState.recordTime,
            onChange = onRecordTimeChanged,
        )

        if (viewState.recording) {
            Text(
                modifier = Modifier.padding(start = 16.dp, top = 8.dp),
                text = AppRes.string.record_in_progress,
                style = JetHabitTheme.typography.body,
                color = JetHabitTheme.colors.primaryText,
            )
        }
    }
}