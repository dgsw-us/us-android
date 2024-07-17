package kr.us.us_android.data.community

import java.time.LocalDateTime

data class BoardResponse(
    val code: String,
    val message: String,
    val data: List<BoardInformation>
)

data class BoardInformation(
    val id: Int,
    val title: String,
    val description: String,
    val writer: Writer,
    val category: String,
    val comments: List<Comment>,
    val regDate: String,
    val modDate: String
)

data class Writer(
    val id: Int,
    val username: String,
    val birthDate: String,
    val userId: String,
    val email: String
)

data class Comment(
    val id: Int,
    val content: String,
    val writer: Writer,
    val regDate: String,
    val modDate: String
)