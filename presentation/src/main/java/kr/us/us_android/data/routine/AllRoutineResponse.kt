package kr.us.us_android.data.routine

data class AllRoutineResponse(
    val code: String,
    val message: String,
    val data: List<DataItem>
)

data class DataItem(
    val id: Int,
    val name: String,
    val exerciseList: List<Exercise>
)

data class Exercise(
    val id: Int,
    val name: String
)