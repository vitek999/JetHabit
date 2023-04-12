package ui.themes.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ui.themes.JetHabitTheme

@Composable
internal fun ScreenHeader(
    modifier: Modifier = Modifier,
    title: String,
    backEnabled: Boolean = true,
    onBackClick: () -> Unit = { },
) {
    Row(
        modifier = modifier.padding(top = 8.dp, start = 8.dp, end = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        if ( backEnabled) {
            Icon(
                modifier = Modifier
                    .clickable(onClick = onBackClick, enabled = backEnabled)
                    .size(32.dp),
                imageVector = Icons.Filled.ArrowBack,
                contentDescription = "Back",
                tint = JetHabitTheme.colors.controlColor
            )
        }
        Text(
            modifier = Modifier.fillMaxWidth().padding(start = 8.dp),
            text = title,
            style = JetHabitTheme.typography.heading,
            color = JetHabitTheme.colors.primaryText,
        )
    }
}