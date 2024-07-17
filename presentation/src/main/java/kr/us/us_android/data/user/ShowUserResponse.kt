package kr.us.us_android.data.user

data class ShowUserResponse(
    val code: String,
    val message: String,
    val data: UserData
)

data class UserData(
    val id: Int,
    val username: String,
    val birthDate: String,
    val userId: String,
    val email: String
)