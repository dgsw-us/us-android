package kr.us.us_android.feature.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kr.us.us_android.R
import kr.us.us_android.data.routine.DataItem

class RoutineAdapter(
    private val dataList: List<DataItem>,
    private val itemClickListener: (DataItem) -> Unit
) : RecyclerView.Adapter<RoutineAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_routine, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = dataList[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val routineId: TextView = itemView.findViewById(R.id.routineId)
        private val routineName: TextView = itemView.findViewById(R.id.routineTitle)

        fun bind(item: DataItem) {
            routineId.text = item.id.toString()
            routineName.text = item.name

            itemView.setOnClickListener {
                itemClickListener.invoke(item)
            }
        }
    }
}
