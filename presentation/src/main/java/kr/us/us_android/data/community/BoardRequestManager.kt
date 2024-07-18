package kr.us.us_android.data.community

import kr.us.us_android.data.info.response.AddInfoResponse
import okhttp3.OkHttpClient
import retrofit2.HttpException
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object BoardRequestManager {

    private val okHttpClient = OkHttpClient.Builder()
        .connectTimeout(30, TimeUnit.SECONDS)  // 연결 타임아웃
        .writeTimeout(30, TimeUnit.SECONDS)    // 쓰기 타임아웃
        .readTimeout(30, TimeUnit.SECONDS)     // 읽기 타임아웃
        .build()

    private val retrofit = Retrofit.Builder()
        .baseUrl("http://192.168.206.97:8080/")
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val boardService: BoardService = BoardRequestManager.retrofit.create(BoardService::class.java)

    suspend fun createBoardRequest(token: String, createBoardData: CreateBoardRequest): Response<AddInfoResponse> {
        val response = boardService.createBoard(token, createBoardData)
        if (!response.isSuccessful)
            throw HttpException(response)

        return response
    }

    suspend fun boardListRequest(token: String): Response<BoardDetailListResponse> {
        return BoardRequestManager.boardService.getInfoList(token)
    }

    suspend fun getBoardRequest(token: String, boardId: Int): Response<BoardDetailResponse> {
        val response = boardService.getBoard(token, boardId)
        if (!response.isSuccessful) {
            throw HttpException(response)
        }
        return response
    }
}