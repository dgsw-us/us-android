package kr.us.us_android.feature.community

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
import kr.us.us_android.data.community.BoardRequestManager
import kr.us.us_android.data.community.CreateBoardRequest
import kr.us.us_android.databinding.FragmentAddBoardBinding
import kr.us.us_android.databinding.FragmentAddInfoBinding
import kr.us.us_android.util.shortToast
import retrofit2.HttpException
import java.net.SocketTimeoutException

class AddBoardFragment : Fragment() {

    private lateinit var binding: FragmentAddBoardBinding
    private lateinit var titlle: String
    private lateinit var description: String
    private lateinit var category: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddBoardBinding.inflate(inflater, container, false)

        binding.ok.setOnClickListener {
            createBoardRequest()
        }

        return binding.root
    }

    private fun createBoardRequest() {
        titlle = binding.title.text.toString()
        description = binding.description.text.toString()
        category = binding.category.text.toString()

        val createBoardRequest = CreateBoardRequest(titlle, description, category)

        viewLifecycleOwner.lifecycleScope.launch {
            try {
                val token = "Bearer ${UsApplication.prefs.token}"
                val response = BoardRequestManager.createBoardRequest(token, createBoardRequest)
                Log.d(Constant.TAG, "response.header : ${response.code()}")

                context?.shortToast("글 추가 성공")

            } catch (e: Exception) {
                e.message
            }
        }
    }
}