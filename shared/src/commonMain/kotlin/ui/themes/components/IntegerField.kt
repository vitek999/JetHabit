package ui.themes.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import ui.themes.JetHabitTheme

@Composable
internal fun IntegerField(
    modifier: Modifier = Modifier,
    value: Int,
    label: String? = null,
    onChange: (Int) -> Unit,
) {
    TextField(
        modifier = modifier.fillMaxWidth(),
        value = value.toString(),
        singleLine = true,
        label = label?.let { { LabelTextView(label) } },
        onValueChange = { onChange(it.toIntOrNull() ?: 0) },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        colors = TextFieldDefaults.textFieldColors(
            backgroundColor = JetHabitTheme.colors.primaryBackground,
            textColor = JetHabitTheme.colors.primaryText,
            focusedIndicatorColor = JetHabitTheme.colors.tintColor,
            disabledIndicatorColor = JetHabitTheme.colors.controlColor,
            cursorColor = JetHabitTheme.colors.tintColor
        )
    )
}