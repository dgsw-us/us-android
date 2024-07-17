package com.example.palette.ui.main.settings

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import kr.us.us_android.MainActivity
import kr.us.us_android.R
import kr.us.us_android.application.UsApplication
import kr.us.us_android.data.auth.AuthRequestManager
import kr.us.us_android.databinding.FragmentChangePasswordBinding
import kr.us.us_android.util.shortToast
import retrofit2.HttpException

class ChangePasswordFragment : Fragment() {

    private lateinit var binding: FragmentChangePasswordBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentChangePasswordBinding.inflate(inflater, container, false)
        (activity as MainActivity).findViewById<View>(R.id.bottom_nav).visibility = View.GONE
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.change.setOnClickListener {
            val beforePassword = binding.beforePassword.text.toString().trim()
            val afterPassword = binding.afterPassword.text.toString().trim()

            if (beforePassword.isEmpty() || afterPassword.isEmpty()) {
                context?.shortToast("이전 비밀번호와 변경할 비밀번호를 입력해주세요.")
                return@setOnClickListener
            }
            changePassword(beforePassword, afterPassword)
        }
    }

    private fun changePassword(beforePassword: String, afterPassword: String) {
        Log.d("ㅇㅇㅇ", UsApplication.prefs.token)
        viewLifecycleOwner.lifecycleScope.launch {
            try {
                val response = AuthRequestManager.changePasswordRequest(
                    "Bearer ${UsApplication.prefs.token}",
                    beforePassword,
                    afterPassword
                )

                if (response.isSuccessful) {
                    context?.shortToast("비밀번호가 성공적으로 변경되었습니다.")
                    Log.d("ChangePasswordFragment", "Password changed successfully.")

                } else {
                    context?.shortToast("비밀번호 변경에 실패했습니다.")
                    Log.e("ChangePasswordFragment", "Failed to change password: ${response.code()} - ${response.message()}")
                }
            } catch (e: HttpException) {
                context?.shortToast("서버 오류가 발생했습니다.")
                Log.e("ChangePasswordFragment", "Server error", e)
            } catch (e: Exception) {
                context?.shortToast("네트워크 오류가 발생했습니다.")
                Log.e("ChangePasswordFragment", "Network error", e)
            }
        }
    }

    override fun onPause() {
        super.onPause()
        (activity as MainActivity).findViewById<View>(R.id.bottom_nav).visibility = View.VISIBLE
    }
}