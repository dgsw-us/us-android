package kr.us.us_android.feature.menu

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.palette.ui.main.settings.ChangePasswordFragment
import kr.us.us_android.R
import kr.us.us_android.databinding.FragmentMenuBinding
import kr.us.us_android.feature.routine.AddRoutineFragment
import kr.us.us_android.feature.routine.PickRoutineFragment

class MenuFragment : Fragment() {

    private lateinit var binding: FragmentMenuBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMenuBinding.inflate(inflater, container, false)

        binding.addRoutine.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction()
                .setCustomAnimations(
                    R.anim.anim_slide_in_from_bottom,
                    R.anim.anim_fade_out_150,
                    R.anim.anim_fade_in_150,
                    R.anim.anim_fade_out_150
                )
                .replace(R.id.main_frame_container, AddRoutineFragment())
                .addToBackStack(null)
                .commitAllowingStateLoss()
        }

        binding.pickRoutine.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction()
                .setCustomAnimations(
                    R.anim.anim_slide_in_from_bottom,
                    R.anim.anim_fade_out_150,
                    R.anim.anim_fade_in_150,
                    R.anim.anim_fade_out_150
                )
                .replace(R.id.main_frame_container, PickRoutineFragment())
                .addToBackStack(null)
                .commitAllowingStateLoss()
        }

        return binding.root
    }
}