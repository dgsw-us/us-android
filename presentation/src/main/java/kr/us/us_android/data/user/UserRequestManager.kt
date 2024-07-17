package kr.us.us_android.data.user

import kr.us.us_android.data.auth.AuthRequestManager
import kr.us.us_android.data.auth.request.LoginRequest
import kr.us.us_android.data.auth.response.LoginResponse
import kr.us.us_android.data.info.InfoService
import retrofit2.HttpException
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object UserRequestManager {
    private val retrofit = Retrofit.Builder()
        .baseUrl("http://us.baekjoon.kr/")
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