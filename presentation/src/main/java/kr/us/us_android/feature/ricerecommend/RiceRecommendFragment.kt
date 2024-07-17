package kr.us.us_android.feature.ricerecommend

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import kr.us.us_android.R
import kr.us.us_android.application.UsApplication
import kr.us.us_android.data.recommend.RiceRequestManager
import kr.us.us_android.databinding.FragmentRiceRecommendBinding
import kr.us.us_android.feature.home.AddInfoFragment
import kr.us.us_android.util.shortToast
import retrofit2.HttpException
import java.net.SocketTimeoutException

class RiceRecommendFragment : Fragment() {

    private lateinit var binding: FragmentRiceRecommendBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRiceRecommendBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 서버에서 음식 추천 데이터 가져오기
        binding.btnFood.setOnClickListener {
            fetchFoodRecommendation()
        }

        binding.add.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction()
                .setCustomAnimations(
                    R.anim.anim_slide_in_from_bottom,
                    R.anim.anim_fade_out_150,
                    R.anim.anim_fade_in_150,
                    R.anim.anim_fade_out_150
                )
                .replace(R.id.main_frame_container, AddFoodFragment())
                .addToBackStack(null)
                .commitAllowingStateLoss()
        }
    }

    private fun fetchFoodRecommendation() {
        viewLifecycleOwner.lifecycleScope.launch {
            try {
                val token = UsApplication.prefs.token
                // 서버에서 음식 추천 데이터를 가져오는 비동기 요청
                val response = RiceRequestManager.riceRequest("Bearer $token")

                if (response.isSuccessful) {
                    val foodRecommendation = response.body()?.data?.getOrNull(0)
                    if (foodRecommendation != null) {
                        // 데이터를 화면에 표시
                        binding.random.text = foodRecommendation.name
                        binding.description.text = foodRecommendation.description
                    } else {
                        context?.shortToast("음식 추천 데이터가 없습니다.")
                    }
                } else {
                    context?.shortToast("네트워크 요청 실패: ${response.code()}")
                    Log.d("ㅇㅇㅇ", response.message())
                }

            } catch (e: HttpException) {
                context?.shortToast("네트워크 요청 실패: ${e.code()}")
            } catch (e: SocketTimeoutException) {
                context?.shortToast("네트워크 연결이 불안정합니다. 다시 시도해주세요.")
            } catch (e: Exception) {
                context?.shortToast("알 수 없는 에러 발생")
                Log.d("RiceRecommendFragment", "Error: ${e.message}")
            }
        }
    }
}
