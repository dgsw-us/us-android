package kr.us.us_android.data.user

import kr.us.us_android.data.info.response.InfoListResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header

interface UserService {
    @GET("/user")
    suspend fun getUser(
        @Header("Authorization") token: String
    ): Response<ShowUserResponse>
}