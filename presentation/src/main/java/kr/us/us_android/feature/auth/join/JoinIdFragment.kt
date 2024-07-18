package kr.us.us_android.feature.auth.join

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import kr.us.us_android.R
import kr.us.us_android.databinding.FragmentJoinIdBinding
import kr.us.us_android.util.shortToast

class JoinIdFragment : Fragment() {

    private lateinit var binding: FragmentJoinIdBinding
    private val registerViewModel: JoinViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentJoinIdBinding.inflate(inflater, container, false)

        binding.next.setOnClickListener {
            checkId()
        }

        return binding.root
    }

    private fun checkId() {
        val id = binding.idEditText.text.toString().trim()

        if (id.isEmpty()) {
            context?.shortToast("아아디가 비어있습니다")
        } else {
            registerViewModel.setId(id)
            saveId(id)

            requireActivity().supportFragmentManager.beginTransaction()
                .setCustomAnimations(
                    R.anim.anim_slide_in_from_bottom,
                    R.anim.anim_fade_out_150,
                    R.anim.anim_fade_in_150,
                    R.anim.anim_fade_out_150
                )
                .replace(R.id.main_frame_container, JoinPasswordFragment())
                .addToBackStack(null)
                .commitAllowingStateLoss()
        }
    }

    private fun saveId(id: String) {
        val sharedPref = requireActivity().getSharedPreferences("MyApp", Context.MODE_PRIVATE)
        with (sharedPref.edit()) {
            putString("userId", id)
            commit()
        }
    }
}