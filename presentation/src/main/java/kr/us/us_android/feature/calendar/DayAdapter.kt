package kr.us.us_android.feature.calendar

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import kr.us.us_android.R
import java.util.Date

class DayAdapter(private val tempMonth:Int, private val dayList: MutableList<Date>) : RecyclerView.Adapter<DayAdapter.DayView>() {
    private val ROW = 6
    class DayView(val layout: View): RecyclerView.ViewHolder(layout)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DayView {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_day,parent,false)

        return DayView(view)
    }

    override fun onBindViewHolder(holder: DayView, position: Int) {

        //초기화
        val dayText: TextView = holder.layout.findViewById<TextView>(R.id.item_day_text)


        //날짜 표시
        dayText.text = dayList[position].date.toString()
        if(tempMonth != dayList[position].month) {
            dayText.alpha=0.4f
        }

        // 토요일이면 파란색 || 일요일이면 빨간색으로 색상표시
        if((position + 1) % 7 == 0) {
            dayText.setTextColor(ContextCompat.getColor(holder.layout.context,R.color.primaryColor))
        } else if (position == 0 || position % 7 == 0) {
            dayText.setTextColor(ContextCompat.getColor(holder.layout.context,R.color.red))
        }
    }


    override fun getItemCount(): Int {
        return ROW*7
    }
}