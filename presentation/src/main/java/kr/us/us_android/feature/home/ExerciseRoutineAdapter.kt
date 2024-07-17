package kr.us.us_android.feature.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kr.us.us_android.R  // 필요한 경우 프로젝트의 R 파일로 변경
import kr.us.us_android.data.info.response.Information
import kr.us.us_android.data.routine.DataItem
import kr.us.us_android.data.routine.Exercise

class ExerciseRoutineAdapter(private val dataList: List<Exercise>) :
    RecyclerView.Adapter<ExerciseRoutineAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_exercise_routine, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = dataList[position]

        holder.exerciseRoutineId.text = item.id.toString()
        holder.exerciseRoutineName.text = item.name
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val exerciseRoutineId: TextView = itemView.findViewById(R.id.exerciseRoutineId)
        val exerciseRoutineName: TextView = itemView.findViewById(R.id.exerciseRoutineTitle)
    }
}
