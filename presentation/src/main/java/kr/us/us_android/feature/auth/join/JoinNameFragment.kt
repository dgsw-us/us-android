package kr.us.us_android.feature.auth.join

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import kr.us.us_android.R
import kr.us.us_android.common.Constant
import kr.us.us_android.common.HeaderUtil
import kr.us.us_android.data.auth.AuthRequestManager
import kr.us.us_android.data.auth.request.JoinRequest
import kr.us.us_android.databinding.FragmentJoinNameBinding
import kr.us.us_android.util.shortToast
import retrofit2.HttpException
import java.net.SocketTimeoutException

class JoinNameFragment : Fragment() {

    private lateinit var binding: FragmentJoinNameBinding
    private val joinViewModel: JoinViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentJoinNameBinding.inflate(inflater, container, false)

        binding.join.setOnClickListener {
            checkName()
        }

        return binding.root
    }

    private fun checkName() {
        val name = binding.nameEditText.text.toString()

        if (name.isEmpty()) {
            context?.shortToast("이름이 비어있습니다")
        } else {
            joinViewModel.username.observe(viewLifecycleOwner) {
                binding.nameEditText.setText(
                    it
                )
            }
            joinViewModel.setUsername(binding.nameEditText.text.toString())
            registerRequest()
        }
    }

    private fun registerRequest() {
        // ViewModel에서 데이터를 observe하여 가져옵니다.
        joinViewModel.getRegisterRequestData().observe(viewLifecycleOwner) { registerRequest ->
            // observe에서 새로운 데이터가 전달되었을 때만 동작합니다.
            registerRequest?.let {
                val request = JoinRequest(
                    username = it.username,
                    birthDate = it.birthDate,
                    userId = it.userId,
                    email = it.email,
                    password = it.password,
                )
                Log.d(Constant.TAG, "username: ${it.username}")
                Log.d(Constant.TAG, "birthDate: ${it.birthDate}")
                Log.d(Constant.TAG, "userId: ${it.userId}")
                Log.d(Constant.TAG, "email: ${it.email}")
                Log.d(Constant.TAG, "password: ${it.password}")

                val supervisorJob = SupervisorJob()
                viewLifecycleOwner.lifecycleScope.launch(supervisorJob) {
                    try {
                        val response = AuthRequestManager.joinRequest(request)
                        Log.d(Constant.TAG, "response.header : ${response.code()}")

                        val token = response.headers()[HeaderUtil.AuthorizationToken]
                        Log.d(Constant.TAG, "token is $token")

                        context?.shortToast("회원가입 성공")

                    } catch (e: SocketTimeoutException) {
                        Log.e(Constant.TAG, "Network timeout", e)
                        context?.shortToast("네트워크 연결 시간 초과")

                    } catch (e: HttpException) {
                        Log.e(Constant.TAG, "HTTP error: ${e.code()}", e)
                        Log.e(Constant.TAG, "HTTP error: ${e.response()?.raw()?.request()}", e)
                        context?.shortToast("http 문제 발생")

                    } catch (e: Exception) {
                        Log.e(Constant.TAG, "알 수 없는 오류 발생", e)
                        context?.shortToast("알 수 없는 오류 발생")
                    }
                }
            } ?: run {
                Log.e(Constant.TAG, "registerRequest is null")
                context?.shortToast("회원가입 데이터를 가져오는 데 실패했습니다")
            }
        }
    }
}