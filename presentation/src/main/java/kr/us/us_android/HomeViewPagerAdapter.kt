package kr.us.us_android

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kr.us.us_android.R

class HomeViewPagerAdapter(
    private val context: Context,
    private val homeViewPagerData: Array<Pair<String, String>> // 이미지 URL과 이동할 URL을 포함하는 Pair 배열
) : RecyclerView.Adapter<HomeViewPagerAdapter.HomeViewPagerViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewPagerViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_home_viewpager, parent, false)
        return HomeViewPagerViewHolder(view)
    }

    override fun onBindViewHolder(holder: HomeViewPagerViewHolder, position: Int) {
        holder.bindSliderImage(homeViewPagerData[position])
    }

    override fun getItemCount(): Int {
        return homeViewPagerData.size
    }

    inner class HomeViewPagerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val mImageView: ImageView = itemView.findViewById(R.id.homeViewHolderImage)

        fun bindSliderImage(data: Pair<String, String>) {
            val (imageURL, linkURL) = data
            Glide.with(context)
                .load(imageURL)
                .into(mImageView)

            // 이미지 클릭 리스너 추가
            mImageView.setOnClickListener {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(linkURL))
                context.startActivity(intent)
            }
        }
    }
}
