package kr.us.us_android.data.community

import java.time.LocalDateTime

data class BoardResponse(
    val code: String,
    val message: String,
    val data: List<Information>
)

data class Information(
    val id: Int,
    val title: String,
    val description: String,
    val writer: Writer,
    val category: String,
    val comments: List<Comment>,
    val regDate: LocalDateTime,
    val modDate: LocalDateTime
)

data class Writer(
    val id: Int,
    val username: String,
    val birthDate: LocalDateTime,
    val userId: String,
    val email: String
)

data class Comment(
    val id: Int,
    val content: String,
    val writer: Writer,
    val regDate: LocalDateTime,
    val modDate: LocalDateTime
)