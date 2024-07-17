package kr.us.us_android.feature.community

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kr.us.us_android.R
import kr.us.us_android.data.community.BoardInformation
import kr.us.us_android.data.info.response.Information

class CommunityAdapter(
    private val dataList: List<BoardInformation>,
    private val itemClickListener: (BoardInformation) -> Unit
) : RecyclerView.Adapter<CommunityAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_community, parent, false)
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
        private val boardTitle: TextView = itemView.findViewById(R.id.board_title)
        private val boardCategory: TextView = itemView.findViewById(R.id.board_category)

        fun bind(item: BoardInformation) {
            boardTitle.text = item.title
            boardCategory.text = item.category

            itemView.setOnClickListener {
                itemClickListener.invoke(item)
            }
        }
    }
}
