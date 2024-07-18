package kr.us.us_android.feature.ricerecommend

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import kr.us.us_android.application.UsApplication
import kr.us.us_android.common.Constant
import kr.us.us_android.data.info.InfoRequestManager
import kr.us.us_android.data.user.AddFoodRequest
import kr.us.us_android.databinding.FragmentAddFoodBinding
import kr.us.us_android.util.shortToast

class AddFoodFragment : Fragment() {

    private lateinit var binding: FragmentAddFoodBinding
    private lateinit var name: String
    private lateinit var description: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddFoodBinding.inflate(inflater, container, false)

        binding.btnContinue.setOnClickListener {
            addFoodRequest()
        }

        return binding.root
    }

    private fun addFoodRequest() {
        name = binding.name.text.toString().trim()
        description = binding.description.text.toString().trim()

        val addFoodRequest = AddFoodRequest(name, description)

        viewLifecycleOwner.lifecycleScope.launch {
            try {
                val token = "Bearer ${UsApplication.prefs.token}"
                val response = InfoRequestManager.addFoodRequest(token, addFoodRequest)
                Log.d(Constant.TAG, "response.header : ${response.code()}")
                if (response.isSuccessful) {
                    context?.shortToast("추가 성공")
                }
            } catch (e: Exception) {
                context?.shortToast("추가 실패")
                Log.d("ㅇㅇㅇ", e.stackTraceToString())
            }
        }
    }
}