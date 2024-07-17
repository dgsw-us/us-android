package kr.us.us_android.data.info

import kr.us.us_android.data.info.response.InfoListResponse
import retrofit2.Response
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header

interface InfoService {
    @GET("/info/list")
    suspend fun getInfoList(
        @Header("Authorization") token: String
    ): Response<InfoListResponse>
}