package kr.us.us_android.feature.home

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.viewpager2.widget.ViewPager2
import kr.us.us_android.R
import kr.us.us_android.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var homeViewPager: ViewPager2
    private lateinit var layoutIndicator: LinearLayout

    private val homeViewPagerData = arrayOf(
        Pair("https://2030.go.kr/static/yth/img/ythRenew/img-visual2023-m0104.png", "https://2030.go.kr/main"),
        Pair("https://mediahub.seoul.go.kr/uploads/mediahub/2024/03/dqycZPdZTvZFhxHkiDAQCOAeuAQZTCJR.png", "https://mediahub.seoul.go.kr/archives/2010478"),
        Pair("https://www.korea.kr/newsWeb/resources/attaches/2022.03/16/e6d5fde99686a5591441a97ae50c0e2d.jpg", "https://www.korea.kr/news/policyNewsView.do?newsId=148899902#policyNews")
    )

    private val handler = Handler(Looper.getMainLooper())
    private val sliderRunnable = object : Runnable {
        override fun run() {
            val itemCount = homeViewPager.adapter?.itemCount ?: 0
            val currentItem = homeViewPager.currentItem
            val nextItem = (currentItem + 1) % itemCount
            homeViewPager.setCurrentItem(nextItem, true)
            handler.postDelayed(this, 5000)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        layoutIndicator = binding.root.findViewById(R.id.layoutIndicators)

        homeViewPager = binding.homeViewPager

        homeViewPager.adapter = HomeViewPagerAdapter(requireContext(), homeViewPagerData)

        homeViewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                // Ensure fragment is attached to context before calling setCurrentIndicator
                if (isAdded) {
                    setCurrentIndicator(position)
                }
            }
        })

        setupIndicators(homeViewPagerData.size)

        handler.postDelayed(sliderRunnable, 5000)

        return binding.root
    }

    private fun setupIndicators(count: Int) {
        val indicators = arrayOfNulls<ImageView>(count)
        val params = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        params.setMargins(8, 8, 8, 8)

        for (i in indicators.indices) {
            indicators[i] = ImageView(requireContext()).apply {
                setImageDrawable(
                    ContextCompat.getDrawable(
                        requireContext(),
                        R.drawable.indicator
                    )
                )
                layoutParams = params
                layoutParams.width = resources.getDimensionPixelSize(R.dimen.indicator_size)
                layoutParams.height = resources.getDimensionPixelSize(R.dimen.indicator_size)
            }
            layoutIndicator.addView(indicators[i])
        }
        setCurrentIndicator(0)
    }

    private fun setCurrentIndicator(selectedPosition: Int) {
        val indicatorCount = layoutIndicator.childCount
        for (i in 0 until indicatorCount) {
            val indicatorView = layoutIndicator.getChildAt(i) as? ImageView
            if (indicatorView != null) {
                val colorRes = if (i == selectedPosition) R.color.indicator_active else R.color.indicator_inactive
                val color = ContextCompat.getColor(requireContext(), colorRes)
                indicatorView.setColorFilter(color)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        handler.removeCallbacks(sliderRunnable)
    }
}