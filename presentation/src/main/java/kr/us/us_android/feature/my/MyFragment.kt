package kr.us.us_android.feature.my

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.palette.ui.main.settings.ChangePasswordFragment
import kotlinx.coroutines.launch
import kr.us.us_android.R
import kr.us.us_android.application.PreferenceManager
import kr.us.us_android.application.UsApplication
import kr.us.us_android.application.UserPrefs
import kr.us.us_android.data.auth.AuthRequestManager
import kr.us.us_android.data.user.UserRequestManager
import kr.us.us_android.databinding.FragmentMyBinding
import kr.us.us_android.feature.auth.login.LoginFragment
import kr.us.us_android.feature.notification.NotificationFragment
import kr.us.us_android.setting.SettingFragment
import kr.us.us_android.util.shortToast
import retrofit2.HttpException
import java.net.SocketTimeoutException

class MyFragment : Fragment() {

    private lateinit var binding: FragmentMyBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMyBinding.inflate(inflater, container, false)

        loadProfileInfo()

        if (!UserPrefs.isInitialized) {
            UserPrefs.init(requireContext())
        }

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

        binding.setting.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction()
                .setCustomAnimations(
                    R.anim.anim_slide_in_from_bottom,
                    R.anim.anim_fade_out_150,
                    R.anim.anim_fade_in_150,
                    R.anim.anim_fade_out_150
                )
                .replace(R.id.main_frame_container, SettingFragment())
                .addToBackStack(null)
                .commitAllowingStateLoss()
        }

        binding.loginText.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction()
                .setCustomAnimations(
                    R.anim.anim_slide_in_from_bottom,
                    R.anim.anim_fade_out_150,
                    R.anim.anim_fade_in_150,
                    R.anim.anim_fade_out_150
                )
                .replace(R.id.main_frame_container, LoginFragment())
                .addToBackStack(null)
                .commitAllowingStateLoss()
        }

        binding.changePassword.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction()
                .setCustomAnimations(
                    R.anim.anim_slide_in_from_bottom,
                    R.anim.anim_fade_out_150,
                    R.anim.anim_fade_in_150,
                    R.anim.anim_fade_out_150
                )
                .replace(R.id.main_frame_container, ChangePasswordFragment())
                .addToBackStack(null)
                .commitAllowingStateLoss()
        }

        binding.logout.setOnClickListener {
            logout()
        }

        binding.resign.setOnClickListener {
            resign()
        }

        return binding.root
    }

    private fun logout() {

        UsApplication.prefs = PreferenceManager(requireContext().applicationContext)
        UsApplication.prefs.clearToken()
        UserPrefs.clearUserData()
        context?.shortToast("로그아웃 성공")

        requireActivity().supportFragmentManager.beginTransaction()
            .setCustomAnimations(
                R.anim.anim_slide_in_from_bottom,
                R.anim.anim_fade_out_150,
                R.anim.anim_fade_in_150,
                R.anim.anim_fade_out_150
            )
            .replace(R.id.main_frame_container, LoginFragment())
            .addToBackStack(null)
            .commitAllowingStateLoss()
    }

    private fun resign() {
        viewLifecycleOwner.lifecycleScope.launch {
            try {
                val token = UsApplication.prefs.token
                // 서버에서 음식 추천 데이터를 가져오는 비동기 요청
                val response = AuthRequestManager.resignRequest("Bearer $token")

                if (response.isSuccessful) {
                    context?.shortToast("회원탈퇴 성공")
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

    private fun loadProfileInfo() {
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

                    binding.loginText.text = "${username}님, 환영합니다!"
                    binding.email.text = email
                    binding.id.text = userId
                    binding.name.text = username
                    binding.birthDate.text = birthDate
                }
            } catch (e: Exception) {
                e.message
            }
        }
    }
}