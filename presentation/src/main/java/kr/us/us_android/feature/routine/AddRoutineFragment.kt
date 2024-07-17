package kr.us.us_android.feature.routine

import android.content.Intent
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
import kr.us.us_android.common.Constant
import kr.us.us_android.data.auth.AuthRequestManager
import kr.us.us_android.data.auth.request.LoginRequest
import kr.us.us_android.data.routine.AddRoutineRequest
import kr.us.us_android.data.routine.RoutineRequestManager
import kr.us.us_android.databinding.FragmentAddFoodBinding
import kr.us.us_android.databinding.FragmentAddRoutineBinding
import kr.us.us_android.util.shortToast
import retrofit2.HttpException
import java.net.SocketTimeoutException

class AddRoutineFragment : Fragment() {

    private lateinit var binding: FragmentAddRoutineBinding
    private lateinit var routine: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddRoutineBinding.inflate(inflater, container, false)

        binding.add.setOnClickListener {
            addRoutineRequest()
        }

        return binding.root
    }

    private fun addRoutineRequest() {
        routine = binding.routine.text.toString().trim()

        val addRoutineRequest = AddRoutineRequest(routine)

        viewLifecycleOwner.lifecycleScope.launch {
            try {
                val token = "Bearer ${UsApplication.prefs.token}"
                val response = RoutineRequestManager.addRoutineRequest(token, addRoutineRequest)
                Log.d(Constant.TAG, "response.header : ${response.code()}")

                if (response.isSuccessful) {
                    context?.shortToast("루틴 추가에 성공했습니다.")
                }
            } catch (e: Exception) {
                e.message
            }
        }
    }
}