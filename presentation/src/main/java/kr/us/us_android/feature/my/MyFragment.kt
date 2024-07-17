package kr.us.us_android.feature.my

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import com.example.palette.ui.main.settings.ChangePasswordFragment
import kotlinx.coroutines.launch
import kr.us.us_android.R
import kr.us.us_android.application.PreferenceManager
import kr.us.us_android.application.UsApplication
import kr.us.us_android.application.UserPrefs
import kr.us.us_android.databinding.FragmentMyBinding
import kr.us.us_android.feature.auth.login.LoginFragment
import kr.us.us_android.feature.home.HomeFragment
import kr.us.us_android.feature.menu.MenuFragment
import kr.us.us_android.feature.notification.NotificationFragment
import kr.us.us_android.setting.SettingFragment
import kr.us.us_android.util.shortToast
import kotlin.math.log

class MyFragment : Fragment() {

    private lateinit var binding: FragmentMyBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMyBinding.inflate(inflater, container, false)

        userProfileInfo()

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
            .replace(R.id.main_frame_container, HomeFragment())
            .addToBackStack(null)
            .commitAllowingStateLoss()
    }

    private fun userProfileInfo() {
        viewLifecycleOwner.lifecycleScope.launch {
            val cachedUserName = UserPrefs.userName
            val cachedUserEmail = UserPrefs.userEmail
            val cachedUserId = UserPrefs.userId
            val cachedUserBirthDate = UserPrefs.userBirthDate

            if (cachedUserName != null && cachedUserEmail != null && cachedUserId != null && cachedUserBirthDate != null) {
                binding.loginText.text = "$cachedUserName"
                binding.name.text = "$cachedUserName"
                binding.email.text = "$cachedUserEmail"
                binding.id.text = "$cachedUserId"
                binding.birthDate.text = "$cachedUserBirthDate"
            } else {
                binding.loginText.text = "여기를 눌러 로그인하세요"
                binding.name.text = "..."
                binding.email.text = "..."
                binding.id.text = "..."
                binding.birthDate.text = "..."
            }
        }
    }
}