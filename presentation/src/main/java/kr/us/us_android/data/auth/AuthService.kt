package kr.us.us_android.data.auth

import kr.us.us_android.data.auth.request.LoginRequest
import kr.us.us_android.data.auth.response.LoginResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthService {
    @POST("/user/login")
    suspend fun login(
        @Body loginRequest: LoginRequest
    ): Response<LoginResponse>
}