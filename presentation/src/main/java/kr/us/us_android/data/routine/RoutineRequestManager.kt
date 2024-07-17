package kr.us.us_android.data.routine

import kr.us.us_android.data.auth.response.DeleteUserResponse
import kr.us.us_android.data.info.response.AddInfoResponse
import okhttp3.OkHttpClient
import retrofit2.HttpException
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RoutineRequestManager {

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

    val routineService: RoutineService = retrofit.create(RoutineService::class.java)

    suspend fun addRoutineRequest(token: String, addRoutineData: AddRoutineRequest): Response<AddInfoResponse> {
        val response = routineService.addRoutine(token, addRoutineData)
        if (!response.isSuccessful)
            throw HttpException(response)

        return response
    }

    suspend fun addExerciseRoutineRequest(token: String, addRoutineData: AddRoutineRequest): Response<AddInfoResponse> {
        val response = routineService.addExerciseRoutine(token, addRoutineData)
        if (!response.isSuccessful)
            throw HttpException(response)

        return response
    }

    suspend fun allRoutineRequest(token: String): Response<AllRoutineResponse> {
        return routineService.getInfoList(token)
    }

    suspend fun deleteRoutineRequest(token: String, routineId: Int): Response<DeleteUserResponse> {
        val response = routineService.deleteRoutine("Bearer $token", routineId)
        if (!response.isSuccessful) {
            throw HttpException(response)
        }
        return response
    }
}