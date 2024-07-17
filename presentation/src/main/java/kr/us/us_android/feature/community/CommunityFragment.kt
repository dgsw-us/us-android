package kr.us.us_android.feature.community

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kr.us.us_android.R
import kr.us.us_android.application.UsApplication
import kr.us.us_android.data.community.BoardInformation
import kr.us.us_android.data.community.BoardRequestManager
import kr.us.us_android.data.info.InfoRequestManager
import kr.us.us_android.databinding.FragmentCommunityBinding
import kr.us.us_android.feature.home.AddInfoFragment
import kr.us.us_android.feature.home.InformationAdapter
import kr.us.us_android.util.shortToast
import retrofit2.HttpException
import java.net.SocketTimeoutException
import kotlin.coroutines.cancellation.CancellationException

class CommunityFragment : Fragment() {

    private lateinit var binding: FragmentCommunityBinding
    private lateinit var communityRecyclerView: RecyclerView
    private lateinit var communityAdapter: CommunityAdapter
    private var boardDataList: List<BoardInformation> = emptyList()

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
                    val dataList = response.body()?.data ?: emptyList()
                    boardDataList = dataList
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

        lifecycleScope.launch {
            try {
                val token = UsApplication.prefs.token
                val id = item.id

                // DELETE 요청 보내기
                val response = InfoRequestManager.deleteInfoRequest("Bearer $token", id)

                if (response.isSuccessful) {
                    context?.shortToast("삭제 성공")
                    getBoardList() // 성공적으로 삭제 후 목록 갱신
                } else {
                    context?.shortToast("네트워크 요청 실패: ${response.code()}")
                    Log.d("HomeFragment", "네트워크 요청 실패: ${response.message()}")
                }

            } catch (e: HttpException) {
                context?.shortToast("네트워크 요청 실패: ${e.code()}")
                Log.e("HomeFragment", "HttpException: ${e.message()}")
            } catch (e: SocketTimeoutException) {
                context?.shortToast("네트워크 연결이 불안정합니다. 다시 시도해주세요.")
                Log.e("HomeFragment", "SocketTimeoutException: ${e.message}")
            } catch (e: Exception) {
                context?.shortToast("알 수 없는 에러 발생")
                Log.e("HomeFragment", "Error: ${e.message}")
            }
        }
    }
}
