package kr.us.us_android.data.recommend

import kr.us.us_android.data.auth.response.FoodResponse
import kr.us.us_android.data.info.response.InfoListResponse
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RiceRequestManager {

    private val okHttpClient = OkHttpClient.Builder()
        .connectTimeout(30, TimeUnit.SECONDS)  // 연결 타임아웃
        .writeTimeout(30, TimeUnit.SECONDS)    // 쓰기 타임아웃
        .readTimeout(30, TimeUnit.SECONDS)     // 읽기 타임아웃
        .build()

    private val retrofit = Retrofit.Builder()
        .baseUrl("http://us.baekjoon.kr/")
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val riceService: RiceService = retrofit.create(RiceService::class.java)

    suspend fun riceRequest(token: String): Response<FoodResponse> {
        return riceService.getFood(token)
    }
}