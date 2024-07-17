package kr.us.us_android.data.auth.request

data class ChangePasswordRequest(
    val beforePassword: String,
    val afterPassword: String
)
