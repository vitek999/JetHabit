package screens.users.main.models

import data.features.users.models.User

sealed class UsersMainEvent {
    object ActionInvoked : UsersMainEvent()
    object OnCreateUserClicked : UsersMainEvent()

    object OnBackClicked : UsersMainEvent()
    data class OnUserClicked(val user: User) : UsersMainEvent()
    data class OnUserSetSelected(val user: User) : UsersMainEvent()
}