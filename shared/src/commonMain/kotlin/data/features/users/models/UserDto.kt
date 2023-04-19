package data.features.users.models

import kotlinx.serialization.Serializable

@Serializable
data class UserDto(
    val id: Long,
    val firstName: String,
    val lastName: String,
    val age: Int,
    val weight: Int,
    val height: Int,
    val gender: Gender,
)

fun User.asDto(): UserDto = UserDto(id, firstName, lastName, age, weight, height, gender)