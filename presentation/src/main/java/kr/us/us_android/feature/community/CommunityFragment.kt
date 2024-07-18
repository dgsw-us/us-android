package kr.us.us_android.feature.community

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kr.us.us_android.R
import kr.us.us_android.application.UsApplication
import kr.us.us_android.data.community.BoardInformation
import kr.us.us_android.data.community.BoardRequestManager
import kr.us.us_android.databinding.FragmentCommunityBinding
import kr.us.us_android.util.shortToast
import kotlin.coroutines.cancellation.CancellationException

class CommunityFragment : Fragment() {

    private lateinit var binding: FragmentCommunityBinding
    private lateinit var communityRecyclerView: RecyclerView
    private lateinit var communityAdapter: CommunityAdapter
    private var boardDataList: List<BoardInformation> = emptyList<BoardInformation>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCommunityBinding.inflate(inflater, container, false)

        communityRecyclerView = binding.exRecyclerView
        communityRecyclerView.layoutManager = LinearLayoutManager(requireContext())

        binding.add.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction()
                .setCustomAnimations(
                    R.anim.anim_slide_in_from_bottom,
                    R.anim.anim_fade_out_150,
                    R.anim.anim_fade_in_150,
                    R.anim.anim_fade_out_150
                )
                .replace(R.id.main_frame_container, AddBoardFragment())
                .addToBackStack(null)
                .commitAllowingStateLoss()
        }

        communityAdapter = CommunityAdapter(boardDataList) { item ->
            communityOnItemClick(item)
        }

        getBoardList()

        return binding.root
    }

    private fun getBoardList() {
        lifecycleScope.launch(Dispatchers.Main) {
            try {
                val token = UsApplication.prefs.token
                if (token.isEmpty()) {
                    context?.shortToast("로그인 후 다시 시도해주세요.")
                    return@launch
                }

                val response = BoardRequestManager.boardListRequest("Bearer $token")

                if (response.isSuccessful) {
                    val dataList = response.body()!!.data
                    boardDataList = dataList as List<BoardInformation>
                    communityAdapter = CommunityAdapter(boardDataList) { item ->
                        communityOnItemClick(item)
                    }
                    communityRecyclerView.adapter = communityAdapter
                    communityAdapter.notifyDataSetChanged() // 어댑터에 데이터 변경 알리기
                } else {
                    context?.shortToast("네트워크 요청 실패: ${response.code()}")
                }
            } catch (e: CancellationException) {
                // 코루틴 취소 예외 처리
                Log.d("Coroutine", "Coroutine cancelled: ${e.message}")
            } catch (e: Exception) {
                context?.shortToast("네트워크 오류: ${e.message}")
                Log.e("Coroutine", "Coroutine error: ${e.message}", e)
            }
        }
    }

    private fun communityOnItemClick(item: BoardInformation) {
        context?.shortToast("클릭된 아이템 ID: ${item.id}")

        val bundle = Bundle().apply {
            putString("아이디", item.id.toString())
        }

        val communityDetailFragment = CommunityDetailFragment().apply {
            arguments = bundle
        }

        requireActivity().supportFragmentManager.beginTransaction()
            .setCustomAnimations(
                R.anim.anim_slide_in_from_bottom,
                R.anim.anim_fade_out_150,
                R.anim.anim_fade_in_150,
                R.anim.anim_fade_out_150
            )
            .replace(R.id.main_frame_container, communityDetailFragment)
            .addToBackStack(null)
            .commitAllowingStateLoss()
    }
}
