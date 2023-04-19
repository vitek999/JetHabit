package screens.train.settings.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import screens.train.settings.model.TrainSettingsViewState
import tech.mobiledeveloper.shared.AppRes
import ui.themes.components.IntegerField
import ui.themes.components.JetHabitButton
import ui.themes.components.ScreenHeader

@Composable
fun TrainSettingsView(
    viewState: TrainSettingsViewState,
    onBackClick: () -> Unit,
    onSaveClick: () -> Unit,
    onExportClick: () -> Unit,
    onTrainTimeChange: (Int) -> Unit,
) {
    Column(modifier = Modifier.fillMaxSize()) {
        ScreenHeader(
            modifier = Modifier.fillMaxWidth(),
            backEnabled = true,
            title = AppRes.string.settings_title,
            onBackClick = onBackClick,
        )

        IntegerField(
            modifier = Modifier.fillMaxWidth().padding(top = 8.dp, start = 16.dp, end = 16.dp),
            value = viewState.trainTime,
            onChange = onTrainTimeChange,
        )

        JetHabitButton(
            modifier = Modifier.fillMaxWidth().padding(top = 8.dp, start = 16.dp, end = 16.dp),
            text = AppRes.string.export_user_data,
            onClick = onExportClick,
        )

        JetHabitButton(
            modifier = Modifier.fillMaxWidth().padding(top = 8.dp, start = 16.dp, end = 16.dp),
            text = AppRes.string.settings_save_button_text,
            onClick = onSaveClick,
        )
    }
}