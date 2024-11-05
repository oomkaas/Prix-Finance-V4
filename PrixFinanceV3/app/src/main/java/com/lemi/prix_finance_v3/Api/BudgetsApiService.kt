import retrofit2.Call
import retrofit2.http.*

interface BudgetsApiService {
    @GET("api/Budgets")
    fun getBudgets(): Call<List<Budget>>

    @GET("api/Budgets/{id}")
    fun getBudget(@Path("id") id: Int): Call<Budget>

    @POST("api/Budgets")
    fun createBudget(@Body budget: BudgetCreateDto): Call<Budget>

    @PUT("api/Budgets/{id}")
    fun updateBudget(@Path("id") id: Int, @Body budget: BudgetUpdateDto): Call<Void>

    @DELETE("api/Budgets/{id}")
    fun deleteBudget(@Path("id") id: Int): Call<Void>
}