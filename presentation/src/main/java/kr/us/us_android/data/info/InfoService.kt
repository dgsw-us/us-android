package kr.us.us_android.data.info

import kr.us.us_android.data.info.request.AddInfoRequest
import kr.us.us_android.data.info.response.AddInfoResponse
import kr.us.us_android.data.info.response.InfoListResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface InfoService {
    @GET("/info/list")
    suspend fun getInfoList(
        @Header("Authorization") token: String
    ): Response<InfoListResponse>

    @POST("/info")
    suspend fun addInfo(
        @Header("Authorization") token: String,
        @Body addInfoRequest: AddInfoRequest
    ): Response<AddInfoResponse>
}