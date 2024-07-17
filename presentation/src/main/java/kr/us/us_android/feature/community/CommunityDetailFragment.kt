package kr.us.us_android.feature.community

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import kr.us.us_android.R
import kr.us.us_android.application.UsApplication
import kr.us.us_android.data.community.BoardRequestManager
import kr.us.us_android.data.routine.AddRoutineRequest
import kr.us.us_android.data.user.UserRequestManager
import kr.us.us_android.databinding.FragmentCommunityBinding
import kr.us.us_android.databinding.FragmentCommunityDetailBinding

class CommunityDetailFragment : Fragment() {

    private lateinit var binding: FragmentCommunityDetailBinding
    private var id: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCommunityDetailBinding.inflate(inflater, container, false)

        arguments?.let {
            id = it.getString("아이디")
        }

        loadBoardRequest()

        return binding.root
    }

    private fun loadBoardRequest() {
        viewLifecycleOwner.lifecycleScope.launch {
            try {
                val token = UsApplication.prefs.token
                val response = BoardRequestManager.getBoardRequest(token, id!!.toInt())

                if (response.isSuccessful) {
                    response.body()?.data?.let { boardInfo ->
                        binding.title.text = boardInfo.title
                        binding.description.text = boardInfo.description
                        binding.category.text = boardInfo.category
                        binding.regDate.text = boardInfo.regDate
                    }
                } else {
                    // 네트워크 요청 실패 처리
                }
            } catch (e: Exception) {
                // 예외 처리 및 로깅
            }
        }
    }
}
