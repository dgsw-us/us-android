package kr.us.us_android.feature.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kr.us.us_android.R  // 필요한 경우 프로젝트의 R 파일로 변경
import kr.us.us_android.data.info.response.Information

class InformationAdapter(private val dataList: List<Information>) :
    RecyclerView.Adapter<InformationAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_information, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = dataList[position]

        holder.textTitle.text = item.title
        holder.textDescription.text = item.description
        holder.textWriter.text = item.writer.username
        holder.textCategory.text = item.category
        holder.textRegDate.text = item.regDate
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textTitle: TextView = itemView.findViewById(R.id.textTitle)
        val textDescription: TextView = itemView.findViewById(R.id.textDescription)
        val textWriter: TextView = itemView.findViewById(R.id.textWriter)
        val textCategory: TextView = itemView.findViewById(R.id.textCategory)
        val textRegDate: TextView = itemView.findViewById(R.id.textRegDate)
    }
}
