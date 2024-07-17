package kr.us.us_android.data.info.response

import com.google.gson.annotations.SerializedName

data class InfoListResponse(
    @SerializedName("code")
    val code: String,

    @SerializedName("message")
    val message: String,

    @SerializedName("data")
    val data: List<Information>
)

data class Information(
    @SerializedName("informationId")
    val informationId: Int,

    @SerializedName("title")
    val title: String,

    @SerializedName("description")
    val description: String,

    @SerializedName("writer")
    val writer: Writer,

    @SerializedName("category")
    val category: String,

    @SerializedName("regDate")
    val regDate: String,

    @SerializedName("modDate")
    val modDate: String
)

data class Writer(
    @SerializedName("id")
    val id: Int,

    @SerializedName("username")
    val username: String,

    @SerializedName("birthDate")
    val birthDate: String,

    @SerializedName("userId")
    val userId: String,

    @SerializedName("email")
    val email: String
)