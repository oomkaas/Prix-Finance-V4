data class Goal(
    val goalId: Int,
    val userId: Int,
    val targetAmount: Double,
    val category: String,
    val amount: Double,
    val period: String
)

data class GoalCreateDto(
    val userId: Int,
    val targetAmount: Double,
    val category: String,
    val amount: Double,
    val period: String
)

data class GoalUpdateDto(
    val goalId: Int,
    val userId: Int,
    val targetAmount: Double,
    val category: String,
    val amount: Double,
    val period: String
)