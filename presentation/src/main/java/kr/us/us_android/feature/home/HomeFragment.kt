package kr.us.us_android.feature.home

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kr.us.us_android.R
import kr.us.us_android.application.UsApplication
import kr.us.us_android.data.info.InfoRequestManager
import kr.us.us_android.data.info.response.Information
import kr.us.us_android.data.routine.DataItem
import kr.us.us_android.data.routine.RoutineRequestManager
import kr.us.us_android.data.user.UserRequestManager
import kr.us.us_android.databinding.FragmentHomeBinding
import kr.us.us_android.feature.menu.MenuFragment
import kr.us.us_android.feature.notification.NotificationFragment
import kr.us.us_android.util.shortToast
import retrofit2.Retrofit
import java.io.Writer
import kotlin.coroutines.cancellation.CancellationException

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var homeViewPager: ViewPager2
    private lateinit var layoutIndicator: LinearLayout
    private lateinit var recyclerView: RecyclerView
    private lateinit var routineRecyclerView: RecyclerView
    private lateinit var informatinAdapter: InformationAdapter
    private lateinit var routineAdapter: RoutineAdapter
    private var informationDataList: List<Information> = emptyList()
    private var routineDataList: List<DataItem> = emptyList()

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

        loadNameInfo()

        binding.add.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction()
                .setCustomAnimations(
                    R.anim.anim_slide_in_from_bottom,
                    R.anim.anim_fade_out_150,
                    R.anim.anim_fade_in_150,
                    R.anim.anim_fade_out_150
                )
                .replace(R.id.main_frame_container, AddInfoFragment())
                .addToBackStack(null)
                .commitAllowingStateLoss()
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = binding.recyclerView
        routineRecyclerView = binding.routineRecyclerView
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        routineRecyclerView.layoutManager = LinearLayoutManager(requireContext())

        GlobalScope.launch(Dispatchers.Main) {
            try {
                val token = UsApplication.prefs.token
                if (token.isEmpty()) {
                    context?.shortToast("로그인 후 다시 시도해주세요.")
                    return@launch
                }

                val response = InfoRequestManager.infoListRequest("Bearer $token")

                if (response.isSuccessful) {
                    val dataList = response.body()?.data ?: emptyList()
                    informatinAdapter = InformationAdapter(dataList)
                    recyclerView.adapter = informatinAdapter
                } else {
                    context?.shortToast("네트워크 요청 실패: ${response.code()}")
                }
            } catch (e: CancellationException) {
                // 코루틴 취소 예외 처리
                Log.d("Coroutine", "Coroutine cancelled: ${e.message}")
            } catch (e: Exception) {
                context?.shortToast("네트워크 오류: ${e.message}")
                Log.e("Coroutine", "Coroutine error: ${e.message}", e)
            }
        }

        GlobalScope.launch(Dispatchers.Main) {
            try {
                val token = UsApplication.prefs.token
                if (token.isEmpty()) {
                    context?.shortToast("로그인 후 다시 시도해주세요.")
                    return@launch
                }

                val response = RoutineRequestManager.allRoutineRequest("Bearer $token")

                if (response.isSuccessful) {
                    val dataList = response.body()?.data ?: emptyList()
                    routineAdapter = RoutineAdapter(dataList)
                    routineRecyclerView.adapter = routineAdapter
                } else {
                    context?.shortToast("네트워크 요청 실패: ${response.code()}")
                }
            } catch (e: CancellationException) {
                // 코루틴 취소 예외 처리
                Log.d("Coroutine", "Coroutine cancelled: ${e.message}")
            } catch (e: Exception) {
                context?.shortToast("네트워크 오류: ${e.message}")
                Log.e("Coroutine", "Coroutine error: ${e.message}", e)
            }
        }


        // 어댑터 설정
        informatinAdapter = InformationAdapter(informationDataList)
        recyclerView.adapter = informatinAdapter

        routineAdapter = RoutineAdapter(routineDataList)
        recyclerView.adapter = informatinAdapter

        layoutIndicator = binding.layoutIndicators
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

        binding.notification.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction()
                .setCustomAnimations(
                    R.anim.anim_slide_in_from_bottom,
                    R.anim.anim_fade_out_150,
                    R.anim.anim_fade_in_150,
                    R.anim.anim_fade_out_150
                )
                .replace(R.id.main_frame_container, NotificationFragment())
                .addToBackStack(null)
                .commitAllowingStateLoss()
        }

        binding.menu.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction()
                .setCustomAnimations(
                    R.anim.anim_slide_in_from_bottom,
                    R.anim.anim_fade_out_150,
                    R.anim.anim_fade_in_150,
                    R.anim.anim_fade_out_150
                )
                .replace(R.id.main_frame_container, MenuFragment())
                .addToBackStack(null)
                .commitAllowingStateLoss()
        }

        binding.recyclerView.setOnClickListener {
            showDeleteConfirmationDialog()
        }
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

    private fun loadNameInfo() {
        viewLifecycleOwner.lifecycleScope.launch {
            try {
                val token = UsApplication.prefs.token
                // 서버에서 음식 추천 데이터를 가져오는 비동기 요청
                val response = UserRequestManager.showUserRequest("Bearer $token")

                if (response.isSuccessful) {
                    val username = response.body()?.data?.username
                    val email = response.body()?.data?.email
                    val userId = response.body()?.data?.userId
                    val birthDate = response.body()?.data?.birthDate

                    binding.userName.text = username
                }
            } catch (e: Exception) {
                e.message
            }
        }
    }

    private fun showDeleteConfirmationDialog() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("삭제 확인")
        builder.setMessage("정말 삭제하시겠습니까?")
        builder.setPositiveButton("예") { dialog, which ->
            // 사용자가 "예"를 선택한 경우
            Log.d("Delete", "사용자가 삭제를 확인했습니다.")
            //여기서 삭제작업수행
        }
        builder.setNegativeButton("아니요") { dialog, which ->
            // 사용자가 "아니요"를 선택한 경우
            Log.d("Delete", "사용자가 삭제를 취소했습니다.")
            dialog.dismiss()
        }
        builder.setOnCancelListener {
            // 다이얼로그가 취소된 경우
            Log.d("Delete", "다이얼로그가 취소되었습니다.")
        }
        builder.show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        handler.removeCallbacks(sliderRunnable)
    }
}
