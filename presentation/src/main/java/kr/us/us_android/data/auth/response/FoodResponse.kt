package kr.us.us_android.data.auth.response

import com.google.gson.annotations.SerializedName

data class FoodResponse(
    @SerializedName("code") val code: String,
    @SerializedName("message") val message: String,
    @SerializedName("data") val data: List<DataItem>
)

data class DataItem(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("description") val description: String
)