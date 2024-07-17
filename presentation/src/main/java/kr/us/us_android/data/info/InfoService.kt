package kr.us.us_android.data.info

import kr.us.us_android.data.auth.response.DeleteUserResponse
import kr.us.us_android.data.info.request.AddInfoRequest
import kr.us.us_android.data.info.response.AddInfoResponse
import kr.us.us_android.data.info.response.InfoListResponse
import kr.us.us_android.data.user.AddFoodRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

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

    @POST("/food")
    suspend fun addFood(
        @Header("Authorization") token: String,
        @Body addFoodRequest: AddFoodRequest
    ): Response<AddInfoResponse>

    @DELETE("/info/{informationId}")
    suspend fun deleteInfo(
        @Header("Authorization") token: String,
        @Path("id") id: Int
    ): Response<DeleteUserResponse>
}