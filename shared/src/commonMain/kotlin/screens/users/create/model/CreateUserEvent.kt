package screens.users.create.model

import data.features.users.models.Gender

sealed class CreateUserEvent {
    data class FirstNameChanged(val newFirstName: String) : CreateUserEvent()
    data class SecondNameChanged(val newSecondName: String) : CreateUserEvent()
    data class AgeChanged(val newAge: Int) : CreateUserEvent()
    data class WeightChanged(val newWeight : Int) : CreateUserEvent()
    data class HeightChanged(val newHeight: Int) : CreateUserEvent()
    data class GenderChanged(val newGender: Gender) : CreateUserEvent()
    object OnSaveClicked : CreateUserEvent()
    object OnBackClicked : CreateUserEvent()
    object ActionInvoked : CreateUserEvent()
}