data class User(
    val userId: Int,
    val firstName: String,
    val lastName: String,
    val email: String,
    val password: String,
    val totalBalance: Double
)

data class UserCreateDto(
    val firstName: String,
    val lastName: String,
    val email: String,
    val password: String,
    val totalBalance: Double
)

data class UserUpdateDto(
    val userId: Int,
    val firstName: String,
    val lastName: String,
    val email: String,
    val password: String,
    val totalBalance: Double
)

