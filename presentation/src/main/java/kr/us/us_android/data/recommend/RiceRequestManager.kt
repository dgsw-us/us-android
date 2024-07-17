package kr.us.us_android.data.recommend

import kr.us.us_android.data.auth.response.FoodResponse
import kr.us.us_android.data.info.response.InfoListResponse
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RiceRequestManager {

    private val retrofit = Retrofit.Builder()
        .baseUrl("http://us.baekjoon.kr/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val riceService: RiceService = retrofit.create(RiceService::class.java)

    suspend fun riceRequest(token: String): Response<FoodResponse> {
        return riceService.getFood(token)
    }
}