data class Transaction(
    val transactionId: Int,
    val userId: Int,
    val transactionName: String,
    val amount: Double,
    val category: String,
    val transactionDate: String // Use String for simplicity, or use a Date type
)

data class TransactionCreateDto(
    val userId: Int,
    val transactionName: String,
    val amount: Double,
    val category: String,
    val transactionDate: String // Use String for simplicity, or use a Date type
)

data class TransactionUpdateDto(
    val transactionId: Int,
    val userId: Int,
    val transactionName: String,
    val amount: Double,
    val category: String,
    val transactionDate: String // Use String for simplicity, or use a Date type
)