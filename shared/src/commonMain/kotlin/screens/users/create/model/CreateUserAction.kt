package screens.users.create.model

sealed class CreateUserAction {
    object UserSaved : CreateUserAction()
    object NavigateBack : CreateUserAction()
}