package kr.us.us_android.data.auth.response

data class LoginResponse(
    val code: String,
    val message: String,
    val tokenData: TokenData
) {
    data class TokenData(
        val grantType: String,
        val accessToken: String,
        val refreshToken: String
    )
}