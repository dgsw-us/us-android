package kr.us.us_android.feature.home

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
import kr.us.us_android.data.info.InfoRequestManager
import kr.us.us_android.data.info.request.AddInfoRequest
import kr.us.us_android.databinding.FragmentAddInfoBinding
import kr.us.us_android.util.shortToast

class AddInfoFragment : Fragment() {

    private lateinit var binding: FragmentAddInfoBinding
    private lateinit var title: String
    private lateinit var description: String
    private lateinit var category: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddInfoBinding.inflate(inflater, container, false)

        (activity as MainActivity).findViewById<View>(R.id.bottom_nav).visibility = View.GONE

        binding.btnContinue.setOnClickListener {
            addInfoRequest()
        }

        return binding.root
    }

    private fun addInfoRequest() {
        title = binding.title.text.toString().trim()
        description = binding.description.text.toString().trim()
        category = binding.category.text.toString().trim()

        val addInfoRequest = AddInfoRequest(title, description, category)

        viewLifecycleOwner.lifecycleScope.launch {
            try {
                val token = "Bearer ${UsApplication.prefs.token}"
                val response = InfoRequestManager.addInfoRequest(token, addInfoRequest)
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

    override fun onPause() {
        super.onPause()
        (activity as MainActivity).findViewById<View>(R.id.bottom_nav).visibility = View.VISIBLE
    }
}