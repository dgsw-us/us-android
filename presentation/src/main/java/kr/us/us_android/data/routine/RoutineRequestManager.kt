package kr.us.us_android.data.routine

import kr.us.us_android.data.auth.response.DeleteUserResponse
import kr.us.us_android.data.info.response.AddInfoResponse
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

    suspend fun deleteRoutineRequest(token: String, routineId: Int): Response<DeleteUserResponse> {
        val response = routineService.deleteRoutine("Bearer $token", routineId)
        if (!response.isSuccessful) {
            throw HttpException(response)
        }
        return response
    }
}