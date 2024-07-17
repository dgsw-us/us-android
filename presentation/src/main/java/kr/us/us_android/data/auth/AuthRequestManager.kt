package kr.us.us_android.data.auth

import kr.us.us_android.data.auth.request.LoginRequest
import kr.us.us_android.data.auth.response.LoginResponse
import retrofit2.HttpException
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object AuthRequestManager {
    private val retrofit = Retrofit.Builder()
        .baseUrl("http://us.baekjoon.kr/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val authService: AuthService = retrofit.create(AuthService::class.java)

    suspend fun loginRequest(loginData: LoginRequest): Response<LoginResponse> {
        val response = authService.login(loginData)
        if (!response.isSuccessful)
            throw HttpException(response)

        return response
    }
}