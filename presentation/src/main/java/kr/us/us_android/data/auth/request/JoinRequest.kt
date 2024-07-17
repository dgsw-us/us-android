package kr.us.us_android.data.auth.request

data class JoinRequest(
    val username: String,
    val birthDate: String,
    val userId: String,
    val email: String,
    val password: String
)
