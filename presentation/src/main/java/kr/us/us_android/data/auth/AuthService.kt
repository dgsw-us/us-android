package kr.us.us_android.data.auth

import kr.us.us_android.data.auth.request.ChangePasswordRequest
import kr.us.us_android.data.auth.request.JoinRequest
import kr.us.us_android.data.auth.request.LoginRequest
import kr.us.us_android.data.auth.response.ChangePasswordResponse
import kr.us.us_android.data.auth.response.DeleteUserResponse
import kr.us.us_android.data.auth.response.JoinResponse
import kr.us.us_android.data.auth.response.LoginResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.Header
import retrofit2.http.PATCH
import retrofit2.http.POST

interface AuthService {
    @POST("/user/login")
    suspend fun login(
        @Body loginRequest: LoginRequest
    ): Response<LoginResponse>

    @POST("/user")
    suspend fun join(
        @Body joinRequest: JoinRequest
    ): Response<JoinResponse>

    @PATCH("/user/password")
    suspend fun changePassword(
        @Header("Authorization") token: String,
        @Body changePasswordRequest: ChangePasswordRequest
    ): Response<ChangePasswordResponse>

    @DELETE("/user")
    suspend fun deleteUser(
        @Header("Authorization") token: String,
    ): Response<DeleteUserResponse>
}