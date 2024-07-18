package kr.us.us_android.data.auth

import kr.us.us_android.data.auth.request.ChangePasswordRequest
import kr.us.us_android.data.auth.request.JoinRequest
import kr.us.us_android.data.auth.request.LoginRequest
import kr.us.us_android.data.auth.response.ChangePasswordResponse
import kr.us.us_android.data.auth.response.DeleteUserResponse
import kr.us.us_android.data.auth.response.JoinResponse
import kr.us.us_android.data.auth.response.LoginResponse
import okhttp3.OkHttpClient
import retrofit2.HttpException
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object AuthRequestManager {

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

    private val authService: AuthService = retrofit.create(AuthService::class.java)

    suspend fun loginRequest(loginData: LoginRequest): Response<LoginResponse> {
        val response = authService.login(loginData)
        if (!response.isSuccessful)
            throw HttpException(response)

        return response
    }

    suspend fun joinRequest(joinData: JoinRequest): Response<JoinResponse> {
        val response = authService.join(joinData)
        if (!response.isSuccessful)
            throw HttpException(response)

        return response
    }

    suspend fun changePasswordRequest(token: String, beforePassword: String, afterPassword: String): Response<ChangePasswordResponse> {
        val request = ChangePasswordRequest(beforePassword, afterPassword)
        val response = authService.changePassword(token, request)

        if (response.code() >= 500)
            throw HttpException(response)

        return response
    }

    suspend fun resignRequest(token: String): Response<DeleteUserResponse> {
        return authService.deleteUser(token)
    }
}