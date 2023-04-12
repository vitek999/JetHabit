package data.features.users

import Database
import data.features.users.models.Gender
import data.features.users.models.User

class UserRepository(private val database: Database) {
    private val users = mutableListOf<User>(User(id = 1L, firstName = "Some", lastName = "Some", age = 20, weight = 20, height = 20, gender = Gender.Female))
    private var selectedUser: User? = null

    suspend fun fetchAllUsers(): List<User> = database.usersQueries.selectAll().executeAsList().map {
        User(
            it.id,
            firstName = it.firstName,
            lastName = it.lastName,
            age = it.age.toInt(),
            weight = it.weight.toInt(),
            height = it.height.toInt(),
            gender = Gender.of(it.gender.toInt())
        )
    }

    suspend fun fetchUserById(id: Long): User? = users.firstOrNull { it.id == id }

    suspend fun createUser(user: User) {
        val id = database.usersQueries.selectLastId().executeAsOneOrNull()?.let { it + 1 } ?: 0
//        val usersCheckStrings = users.map(User::uniqueCheckData)
//        val userCheckString = user.uniqueCheckData
//        if (userCheckString in usersCheckStrings) {
//            throw UserAlreadyExistsException()
//        } else {
//            users += user
//        }
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

    suspend fun getSelectedUser(): User? = selectedUser

    suspend fun setSelectedUser(user: User?) {
        selectedUser = user
    }
}

class UserAlreadyExistsException : RuntimeException()
private val User.uniqueCheckData: String
    get() =  "${firstName}_${lastName}_${age}"