package kr.us.us_android.data.community

import kr.us.us_android.data.auth.response.DeleteUserResponse
import kr.us.us_android.data.info.request.AddInfoRequest
import kr.us.us_android.data.info.response.AddInfoResponse
import kr.us.us_android.data.info.response.InfoListResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

interface BoardService {
    @POST("/board")
    suspend fun createBoard(
        @Header("Authorization") token: String,
        @Body createBoardRequest: CreateBoardRequest
    ): Response<AddInfoResponse>

    @GET("/board/list")
    suspend fun getInfoList(
        @Header("Authorization") token: String
    ): Response<BoardDetailListResponse>

    @GET("/board/{boardId}")
    suspend fun getBoard(
        @Header("Authorization") token: String,
        @Path("boardId") boardId: Int
    ): Response<BoardDetailResponse>
}