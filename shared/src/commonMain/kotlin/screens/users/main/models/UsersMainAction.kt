package screens.users.main.models

sealed class UsersMainAction {
    data class OpenDetailedUser(val userId: Long) : UsersMainAction()
    object OpenUserCreateScreen : UsersMainAction()

    object NavigateBack : UsersMainAction()
}