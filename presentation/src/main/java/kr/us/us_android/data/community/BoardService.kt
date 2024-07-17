package kr.us.us_android.data.community

import kr.us.us_android.data.info.request.AddInfoRequest
import kr.us.us_android.data.info.response.AddInfoResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface BoardService {
    @POST("/board")
    suspend fun createBoard(
        @Header("Authorization") token: String,
        @Body createBoardRequest: CreateBoardRequest
    ): Response<AddInfoResponse>


}