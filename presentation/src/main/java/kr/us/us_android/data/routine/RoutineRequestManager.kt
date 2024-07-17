package kr.us.us_android.data.routine

import kr.us.us_android.data.auth.AuthRequestManager
import kr.us.us_android.data.auth.request.LoginRequest
import kr.us.us_android.data.auth.response.LoginResponse
import kr.us.us_android.data.info.InfoRequestManager
import kr.us.us_android.data.info.response.AddInfoResponse
import kr.us.us_android.data.info.response.InfoListResponse
import retrofit2.HttpException
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RoutineRequestManager {
    private val retrofit = Retrofit.Builder()
        .baseUrl("http://us.baekjoon.kr/")
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
}