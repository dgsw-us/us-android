package kr.us.us_android.data.info

import kr.us.us_android.data.info.response.InfoListResponse
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object InfoRequestManager {

    private val retrofit = Retrofit.Builder()
        .baseUrl("http://us.baekjoon.kr/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val infoService: InfoService = retrofit.create(InfoService::class.java)

    suspend fun infoListRequest(token: String): Response<InfoListResponse> {
        return infoService.getInfoList(token)
    }
}