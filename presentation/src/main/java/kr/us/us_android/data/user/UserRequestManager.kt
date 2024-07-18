package kr.us.us_android.data.user

import okhttp3.OkHttpClient
import retrofit2.HttpException
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object UserRequestManager {

    private val okHttpClient = OkHttpClient.Builder()
        .connectTimeout(30, TimeUnit.SECONDS)  // 연결 타임아웃
        .writeTimeout(30, TimeUnit.SECONDS)    // 쓰기 타임아웃
        .readTimeout(30, TimeUnit.SECONDS)     // 읽기 타임아웃
        .build()

    private val retrofit = Retrofit.Builder()
        .baseUrl("http://192.168.206.97:8080/")
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val userService: UserService = retrofit.create(UserService::class.java)

    suspend fun showUserRequest(token: String): Response<ShowUserResponse> {
        val response = userService.getUser(token)
        if (!response.isSuccessful)
            throw HttpException(response)

        return response
    }
}