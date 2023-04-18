package ui.themes.components

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import ui.themes.JetHabitTheme

@Composable
internal fun LabelTextView(text: String) {
    Text(text = text, style = JetHabitTheme.typography.body, color = JetHabitTheme.colors.secondaryText)
}