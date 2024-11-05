import com.lemi.prix_finance_v3.UserCreateDto
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @GET("api/Users")
    fun getUsers(): Call<List<User>>

    @GET("api/Users/{id}")
    fun getUser(@Path("id") id: Int): Call<User>

    @POST("api/Users")
    fun createUser(@Body user: UserCreateDto): Call<User>

    @PUT("api/Users/{id}")
    fun updateUser(@Path("id") id: Int, @Body user: UserUpdateDto): Call<Void>

    @DELETE("api/Users/{id}")
    fun deleteUser(@Path("id") id: Int): Call<Void>
}