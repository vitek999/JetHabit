package screens.users.detailed.model

sealed class UserDetailedEvent {
    object ActionInvoked : UserDetailedEvent()
}