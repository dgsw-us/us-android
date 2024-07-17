package kr.us.us_android.data.routine

import kr.us.us_android.data.auth.response.DeleteUserResponse
import kr.us.us_android.data.info.response.AddInfoResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

interface RoutineService {
    @POST("/routine")
    suspend fun addRoutine(
        @Header("Authorization") token: String,
        @Body addRoutineRequest: AddRoutineRequest
    ): Response<AddInfoResponse>

    @POST("/routine/exercise")
    suspend fun addExerciseRoutine(
        @Header("Authorization") token: String,
        @Body addRoutineRequest: AddRoutineRequest
    ): Response<AddInfoResponse>

    @GET("/routine/all")
    suspend fun getInfoList(
        @Header("Authorization") token: String
    ): Response<AllRoutineResponse>

    @DELETE("/routine/{id}")
    suspend fun deleteRoutine(
        @Header("Authorization") token: String,
        @Path("id") id: Int
    ): Response<DeleteUserResponse>
}