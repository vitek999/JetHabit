package screens.train.record.view

import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.input.key.*
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.isSecondaryPressed
import androidx.compose.ui.input.pointer.isTertiaryPressed
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import screens.train.record.model.TrainRecordViewState
import tech.mobiledeveloper.shared.AppRes
import ui.themes.JetHabitTheme
import ui.themes.components.IntegerField
import ui.themes.components.JetHabitButton
import ui.themes.components.ScreenHeader
import utils.SnackbarView

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun TrainRecordView(
    viewState: TrainRecordViewState,
    onBackClicked: () -> Unit,
    onStarClick: () -> Unit,
    onStopClick: () -> Unit,
    onSaveExerciseTimestamp: () -> Unit,
    onRecordTimeChanged: (Int) -> Unit,
) {
    val requester = remember { FocusRequester() }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.fillMaxSize()
            .onKeyEvent { event ->
                if (event.type == KeyEventType.KeyUp && event.key == Key.P) {
                    onSaveExerciseTimestamp()
                    return@onKeyEvent true
                }
                if (event.type == KeyEventType.KeyUp && event.key == Key.S) {
                    if (!viewState.recording) {
                        onStarClick()
                    }
                    return@onKeyEvent true
                }
                return@onKeyEvent false
            }
            .focusRequester(requester)
            .focusable()
        ) {
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
                value = viewState.recordTime.toInt(),
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

        SnackbarView(text = viewState.errorText)
    }

    LaunchedEffect(Unit) {
        requester.requestFocus()
    }
}