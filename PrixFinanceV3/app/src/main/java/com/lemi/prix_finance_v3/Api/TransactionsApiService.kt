import retrofit2.Call
import retrofit2.http.*

interface TransactionsApiService {
    @GET("api/Transactions")
    fun getTransactions(): Call<List<Transaction>>

    @GET("api/Transactions/{id}")
    fun getTransaction(@Path("id") id: Int): Call<Transaction>

    @POST("api/Transactions")
    fun createTransaction(@Body transaction: TransactionCreateDto): Call<Transaction>

    @PUT("api/Transactions/{id}")
    fun updateTransaction(@Path("id") id: Int, @Body transaction: TransactionUpdateDto): Call<Void>

    @DELETE("api/Transactions/{id}")
    fun deleteTransaction(@Path("id") id: Int): Call<Void>
}