data class Budget(
    val budgetId: Int,
    val userId: Int,
    val category: String,
    val limit: Double,
    val totalExpenses: Double
)

data class BudgetCreateDto(
    val userId: Int,
    val category: String,
    val limit: Double,
    val totalExpenses: Double
)

data class BudgetUpdateDto(
    val budgetId: Int,
    val userId: Int,
    val category: String,
    val limit: Double,
    val totalExpenses: Double
)