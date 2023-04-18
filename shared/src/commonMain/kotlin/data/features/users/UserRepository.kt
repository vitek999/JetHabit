package data.features.users

import Database
import com.russhwolf.settings.Settings
import com.russhwolf.settings.set
import data.UserEntity
import data.features.users.models.Gender
import data.features.users.models.User

class UserRepository(
    private val database: Database,
    private val settings: Settings,
) {

    suspend fun fetchAllUsers(): List<User> = database.usersQueries.selectAll().executeAsList().map {
        it.asUser()
    }

    suspend fun fetchUserById(id: Long): User? = database.usersQueries.selectById(id).executeAsOneOrNull()?.asUser()

    suspend fun createUser(user: User) {
        val id = database.usersQueries.selectLastId().executeAsOneOrNull()?.let { it + 1 } ?: 0
        val userForInsert = user.copy(id = id)
        database.usersQueries.insert(
            id = userForInsert.id,
            firstName = userForInsert.firstName,
            lastName = userForInsert.lastName,
            age = userForInsert.age.toLong(),
            weight = userForInsert.weight.toLong(),
            height = userForInsert.height.toLong(),
            gender = userForInsert.gender.id.toLong(),
        )
    }

    suspend fun getSelectedUser(): User? = settings.getLongOrNull(SELECTED_USER_KEY)?.let {
        database.usersQueries.selectById(it).executeAsOneOrNull()?.asUser()
    }

    suspend fun setSelectedUser(user: User?) {
        settings[SELECTED_USER_KEY] = user?.id
    }

    companion object {
        private fun UserEntity.asUser(): User = User(
            id = id,
            firstName = firstName,
            lastName = lastName,
            age = age.toInt(),
            weight = weight.toInt(),
            height = height.toInt(),
            gender = Gender.of(gender.toInt())
        )

        private const val SELECTED_USER_KEY = "settings_selected_user_key"
    }
}

class UserAlreadyExistsException : RuntimeException()
private val User.uniqueCheckData: String
    get() =  "${firstName}_${lastName}_${age}"