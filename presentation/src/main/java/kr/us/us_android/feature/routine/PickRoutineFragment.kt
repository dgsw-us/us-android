package kr.us.us_android.feature.routine

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kr.us.us_android.R
import kr.us.us_android.databinding.FragmentPickRoutineBinding

class PickRoutineFragment : Fragment() {

    private lateinit var binding: FragmentPickRoutineBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPickRoutineBinding.inflate(inflater, container, false)

        return binding.root
    }
}