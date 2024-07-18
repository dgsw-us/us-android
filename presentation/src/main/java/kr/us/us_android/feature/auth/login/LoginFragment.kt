package kr.us.us_android.feature.auth.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import kr.us.us_android.MainActivity
import kr.us.us_android.R
import kr.us.us_android.application.UsApplication
import kr.us.us_android.common.Constant.TAG
import kr.us.us_android.data.auth.AuthRequestManager
import kr.us.us_android.data.auth.request.LoginRequest
import kr.us.us_android.databinding.FragmentLoginBinding
import kr.us.us_android.feature.auth.join.JoinEmailFragment
import kr.us.us_android.util.shortToast
import retrofit2.HttpException
import java.net.SocketTimeoutException

class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding
    private lateinit var id: String
    private lateinit var pw: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(inflater, container, false)

        (activity as MainActivity).findViewById<View>(R.id.bottom_nav).visibility = View.GONE

        binding.loginButton.setOnClickListener {
            loginRequest()
        }

        binding.loginToJoin.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction()
                .setCustomAnimations(
                    R.anim.anim_slide_in_from_bottom,
                    R.anim.anim_fade_out_150,
                    R.anim.anim_fade_in_150,
                    R.anim.anim_fade_out_150
                )
                .replace(R.id.main_frame_container, JoinEmailFragment())
                .addToBackStack(null)
                .commitAllowingStateLoss()
        }

        return binding.root
    }


    private fun loginRequest() {
        id = binding.loginIdEdit.text.toString().trim()
        pw = binding.loginPasswordEdit.text.toString().trim()

        val loginRequest = LoginRequest(id, pw)

        viewLifecycleOwner.lifecycleScope.launch {
            try {
                val response = AuthRequestManager.loginRequest(loginRequest)
                Log.d(TAG, "response.header : ${response.code()}")

                if (response.isSuccessful) {
                    val token = response.body()?.data?.refreshToken ?: ""
                    if (token.isNotEmpty()) {
                        UsApplication.prefs.token = token

                        // 로그인 성공 메시지 토스트로 표시
                        context?.shortToast("로그인 성공")

                        // MainActivity로 이동
                        val intent = Intent(activity, MainActivity::class.java)
                        requireActivity().startActivity(intent)
                        activity?.finish()
                    } else {
                        context?.shortToast("토큰이 비어있습니다. 다시 시도해주세요.")
                    }
                } else {
                    context?.shortToast("이메일과 비밀번호를 다시 확인해주세요")
                }
            } catch (e: HttpException) {
                Log.e("LoginFragment", "${e.code()}")
                Log.e("LoginFragment", "${e.message}")
                context?.shortToast("이메일과 비밀번호를 다시 확인해주세요")
            } catch (e: SocketTimeoutException) {
                context?.shortToast("네트워크 연결이 불안정합니다. 다시 시도해주세요.")
            } catch (e: Exception) {
                // HTTP 오류가 아닌 다른 예외가 발생한 경우에 대한 처리
                Log.e("LoginFragment", e.stackTraceToString())
                context?.shortToast("알 수 없는 에러")
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        (activity as MainActivity).findViewById<View>(R.id.bottom_nav).visibility = View.VISIBLE
    }
}