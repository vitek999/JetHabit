package screens.users.create.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.RadioButton
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import data.features.users.models.Gender
import screens.users.create.model.CreateUserViewState
import tech.mobiledeveloper.shared.AppRes
import ui.themes.JetHabitTheme
import ui.themes.components.JetHabitButton
import ui.themes.components.ScreenHeader

@Composable
internal fun CreateUserView(
    viewState: CreateUserViewState,
    onFirstNameChange: (String) -> Unit,
    onSecondNameChange: (String) -> Unit,
    onAgeChange: (Int) -> Unit,
    onGenderChange: (Gender) -> Unit,
    onHeightChange: (Int) -> Unit,
    onWeightChange: (Int) -> Unit,
    onSaveItemClick: () -> Unit,
    onBackClick: () -> Unit,
) {
    val itemModifier = Modifier.padding(top = 8.dp, start = 16.dp, end = 16.dp)

    Column(modifier = Modifier.fillMaxSize()) {
        ScreenHeader(
            title = AppRes.string.user_creation,
            backEnabled = true,
            onBackClick = onBackClick
        )

        CreateUserTextField(
            modifier = itemModifier,
            text = viewState.firstName,
            label = AppRes.string.first_name,
            onChange = onFirstNameChange,
        )
        CreateUserTextField(
            modifier = itemModifier,
            text = viewState.secondName,
            label = AppRes.string.last_name,
            onChange = onSecondNameChange,
        )
        CreateUserIntegerField(
            modifier = itemModifier,
            value = viewState.age,
            label = AppRes.string.age,
            onChange = onAgeChange,
        )
        CreateUserIntegerField(
            modifier = itemModifier,
            value = viewState.height,
            label = AppRes.string.height,
            onChange = onHeightChange,
        )
        CreateUserIntegerField(
            modifier = itemModifier,
            value = viewState.weight,
            label = AppRes.string.weight,
            onChange = onWeightChange,
        )

        Text(
            modifier = itemModifier,
            text = AppRes.string.gender,
            style = JetHabitTheme.typography.body,
            color = JetHabitTheme.colors.primaryText,
        )

        UserCreationRadioButton(
            selected = viewState.gender == Gender.Male,
            label = AppRes.string.male,
            onClick = { onGenderChange(Gender.Male) }
        )

        UserCreationRadioButton(
            selected = viewState.gender == Gender.Female,
            label = AppRes.string.female,
            onClick = { onGenderChange(Gender.Female) }
        )

        JetHabitButton(
            modifier = itemModifier.fillMaxWidth(),
            text = AppRes.string.create_user,
            onClick = onSaveItemClick,
        )
    }
}

@Composable
private fun CreateUserTextField(
    modifier: Modifier = Modifier,
    text: String,
    label: String? = null,
    onChange: (String) -> Unit
) {
    TextField(
        modifier = modifier.fillMaxWidth(),
        value = text,
        label = label?.let { { LabelTextView(label) } },
        singleLine = true,
        onValueChange = onChange,
        colors = TextFieldDefaults.textFieldColors(
            backgroundColor = JetHabitTheme.colors.primaryBackground,
            textColor = JetHabitTheme.colors.primaryText,
            focusedIndicatorColor = JetHabitTheme.colors.tintColor,
            disabledIndicatorColor = JetHabitTheme.colors.controlColor,
            cursorColor = JetHabitTheme.colors.tintColor
        )
    )
}

@Composable
private fun CreateUserIntegerField(
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
        onValueChange = { onChange(it.toInt()) },
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

@Composable
private fun LabelTextView(text: String) {
    Text(text = text, style = JetHabitTheme.typography.body, color = JetHabitTheme.colors.secondaryText)
}

@Composable
private fun UserCreationRadioButton(
    modifier: Modifier = Modifier,
    label: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
        RadioButton(
            selected = selected,
            onClick = onClick,
        )

        Text(
            modifier = Modifier.padding(start = 8.dp).clickable(onClick = onClick),
            text = label,
            style = JetHabitTheme.typography.body,
            color = JetHabitTheme.colors.primaryText,
        )
    }
}

