package kr.us.us_android.data.recommend

import kr.us.us_android.data.auth.response.FoodResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header

interface RiceService {
    @GET("/food")
    suspend fun getFood(
        @Header("Authorization") token: String
    ): Response<FoodResponse>
}