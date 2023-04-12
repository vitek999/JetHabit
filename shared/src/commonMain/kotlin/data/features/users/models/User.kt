package data.features.users.models

data class User(
    val id: Long,
    val firstName: String,
    val lastName: String,
    val age: Int,
    val weight: Int,
    val height: Int,
    val gender: Gender,
)

enum class Gender(val id: Int) {
    Male(0), Female(1);

    companion object {
        fun of(id: Int): Gender = values().firstOrNull { it.id == id } ?: error("Unsupported gender")
    }
}
