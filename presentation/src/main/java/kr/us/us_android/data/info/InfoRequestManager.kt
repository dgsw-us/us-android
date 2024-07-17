package kr.us.us_android.data.info

import kr.us.us_android.data.auth.AuthRequestManager
import kr.us.us_android.data.auth.response.DeleteUserResponse
import kr.us.us_android.data.info.request.AddInfoRequest
import kr.us.us_android.data.info.response.AddInfoResponse
import kr.us.us_android.data.info.response.InfoListResponse
import kr.us.us_android.data.routine.RoutineRequestManager
import kr.us.us_android.data.user.AddFoodRequest
import okhttp3.OkHttpClient
import retrofit2.HttpException
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object InfoRequestManager {

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

    val infoService: InfoService = retrofit.create(InfoService::class.java)

    suspend fun infoListRequest(token: String): Response<InfoListResponse> {
        return infoService.getInfoList(token)
    }

    suspend fun addInfoRequest(token: String, infoData: AddInfoRequest): Response<AddInfoResponse> {
        val response = infoService.addInfo(token, infoData)
        if (!response.isSuccessful)
            throw HttpException(response)

        return response
    }

    suspend fun addFoodRequest(token: String, foodData: AddFoodRequest): Response<AddInfoResponse> {
        val response = infoService.addFood(token, foodData)
        if (!response.isSuccessful)
            throw HttpException(response)

        return response
    }

    suspend fun deleteInfoRequest(token: String, infoId: Int): Response<DeleteUserResponse> {
        val response = infoService.deleteInfo("Bearer $token", infoId)
        if (!response.isSuccessful) {
            throw HttpException(response)
        }
        return response
    }
}