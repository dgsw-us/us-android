package kr.us.us_android.feature.calendar

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kr.us.us_android.R // 여기서 R은 프로젝트의 R 파일을 사용해야 합니다. 필요에 따라 수정하세요.
import java.util.*

class EventAdapter(private val events: List<EventData>) : RecyclerView.Adapter<EventAdapter.EventViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_event, parent, false)
        return EventViewHolder(view)
    }

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        val event = events[position]
        holder.bind(event)
    }

    override fun getItemCount(): Int {
        return events.size
    }

    inner class EventViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val titleTextView: TextView = itemView.findViewById(R.id.event_title)
        private val timeTextView: TextView = itemView.findViewById(R.id.event_time)

        fun bind(event: EventData) {
            titleTextView.text = event.title
            timeTextView.text = event.time.toString()
        }
    }
}
