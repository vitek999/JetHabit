package screens.users.create

import com.adeo.kviewmodel.BaseSharedViewModel
import data.features.users.UserRepository
import data.features.users.models.Gender
import data.features.users.models.User
import di.Inject
import kotlinx.coroutines.launch
import screens.users.create.model.CreateUserAction
import screens.users.create.model.CreateUserEvent
import screens.users.create.model.CreateUserViewState
import utils.ProgressState

class CreateUserViewModel : BaseSharedViewModel<CreateUserViewState, CreateUserAction, CreateUserEvent>(
    initialState = CreateUserViewState()
) {
    private val userRepository = Inject.instance<UserRepository>()

    override fun obtainEvent(viewEvent: CreateUserEvent) {
        when(viewEvent) {
            CreateUserEvent.ActionInvoked -> viewAction = null
            is CreateUserEvent.FirstNameChanged -> handleFirstNameChanged(viewEvent.newFirstName)
            is CreateUserEvent.SecondNameChanged -> handleSecondNameChanged(viewEvent.newSecondName)
            CreateUserEvent.OnSaveClicked -> handleOnSaveClicked()
            is CreateUserEvent.AgeChanged -> handleAgeChanged(viewEvent.newAge)
            is CreateUserEvent.GenderChanged -> handleGenderChange(viewEvent.newGender)
            is CreateUserEvent.HeightChanged -> handleHeightChange(viewEvent.newHeight)
            is CreateUserEvent.WeightChanged -> handleWeightChange(viewEvent.newWeight)
            CreateUserEvent.OnBackClicked -> handleBackClicked()
        }
    }

    private fun handleBackClicked() {
        viewAction = CreateUserAction.NavigateBack
    }

    private fun handleAgeChanged(newAge: Int) {
        viewState = viewState.copy(age = newAge)
    }

    private fun handleGenderChange(newGender: Gender) {
        viewState = viewState.copy(gender = newGender)
    }

    private fun handleHeightChange(newHeight: Int) {
        viewState = viewState.copy(height = newHeight)
    }

    private fun handleWeightChange(newWeight: Int) {
        viewState = viewState.copy(weight = newWeight)
    }

    private fun handleFirstNameChanged(newFirstName: String) {
        viewState = viewState.copy(firstName = newFirstName)
    }

    private fun handleSecondNameChanged(newSecondName: String) {
        viewState = viewState.copy(secondName = newSecondName)
    }

    private fun handleOnSaveClicked() {
        val user = buildUserFromViewState(viewState)
        viewState = viewState.copy(progressState = ProgressState.Loading)
        viewModelScope.launch {
            runCatching {
                userRepository.createUser(user)
                viewState = viewState.copy(progressState = ProgressState.Loaded)
                viewAction = CreateUserAction.UserSaved
            }.onFailure {
                viewState = viewState.copy(progressState = ProgressState.Error)
            }
        }
    }

    companion object {
        private fun buildUserFromViewState(viewState: CreateUserViewState): User = User(
            id = 1L,
            firstName = viewState.firstName,
            lastName = viewState.secondName,
            age = viewState.age,
            weight = viewState.weight,
            height = viewState.height,
            gender = viewState.gender,
        )
    }
}