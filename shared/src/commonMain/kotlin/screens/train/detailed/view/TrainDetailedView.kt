package screens.train.detailed.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import screens.train.detailed.model.TrainDetailedViewState
import ui.themes.JetHabitTheme
import ui.themes.components.JetHabitButton
import ui.themes.components.ScreenHeader

@Composable
fun TrainDetailedView(
    viewState: TrainDetailedViewState,
    onBackClick: () -> Unit,
    onRecordClick: () -> Unit,
    onExportClick: () -> Unit,
    omSavedRecordClick: (id: Long) -> Unit,
    onDeleteClick: (id: Long) -> Unit,
) {
    Column(modifier = Modifier.fillMaxSize()) {
        ScreenHeader(
            title = "${viewState.train?.title}: ${viewState.currentUser?.lastName} ${viewState.currentUser?.firstName?.firstOrNull()}",
            onBackClick = onBackClick,
            backEnabled = true
        )

        JetHabitButton(
            modifier = Modifier.fillMaxWidth().padding(start = 16.dp, end = 16.dp, top = 8.dp),
            text = "Записать",
            onClick = onRecordClick
        )

        JetHabitButton(
            modifier = Modifier.fillMaxWidth().padding(start = 16.dp, end = 16.dp, top = 8.dp),
            text = "Экспорт",
            onClick = onExportClick
        )

        LazyColumn(modifier = Modifier.fillMaxSize().padding(top = 8.dp)) {
            items(viewState.results) { item ->
                Column {
                    Text(
                        modifier = Modifier.clickable { omSavedRecordClick(item.id) }.padding(top = 8.dp),
                        text = item.toString(),
                        style = JetHabitTheme.typography.body,
                        color = JetHabitTheme.colors.primaryText,
                    )
                    JetHabitButton(
                        text = "Удалить",
                        onClick = { onDeleteClick(item.id) }
                    )
                }

            }
        }

    }
}