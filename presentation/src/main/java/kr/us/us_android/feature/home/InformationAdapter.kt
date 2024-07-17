package kr.us.us_android.feature.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kr.us.us_android.R  // 필요한 경우 프로젝트의 R 파일로 변경
import kr.us.us_android.data.info.response.Information

class InformationAdapter(private val dataList: List<Information>, private val itemClickListener: (Information) -> Unit) :
    RecyclerView.Adapter<InformationAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_information, parent, false)
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
        private val textTitle: TextView = itemView.findViewById(R.id.textTitle)
        private val textDescription: TextView = itemView.findViewById(R.id.textDescription)
        private val textWriter: TextView = itemView.findViewById(R.id.textWriter)
        private val textCategory: TextView = itemView.findViewById(R.id.textCategory)
        private val textRegDate: TextView = itemView.findViewById(R.id.textRegDate)

        fun bind(item: Information) {
            textTitle.text = item.title
            textDescription.text = item.description
            textWriter.text = item.writer.username // writer 객체의 특정 필드를 사용
            textCategory.text = item.category
            textRegDate.text = item.regDate

            itemView.setOnClickListener {
                itemClickListener.invoke(item)
            }
        }
    }
}
