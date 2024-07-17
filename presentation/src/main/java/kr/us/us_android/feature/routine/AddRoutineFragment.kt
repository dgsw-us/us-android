package kr.us.us_android.feature.routine

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kr.us.us_android.R
import kr.us.us_android.databinding.FragmentAddFoodBinding
import kr.us.us_android.databinding.FragmentAddRoutineBinding

class AddRoutineFragment : Fragment() {

    private lateinit var binding: FragmentAddRoutineBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddRoutineBinding.inflate(inflater, container, false)

        return binding.root
    }
}