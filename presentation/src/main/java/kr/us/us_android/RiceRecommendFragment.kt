package kr.us.us_android

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kr.us.us_android.databinding.FragmentRiceRecommendBinding

class RiceRecommendFragment : Fragment() {

    private lateinit var binding: FragmentRiceRecommendBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRiceRecommendBinding.inflate(inflater, container, false)

        return binding.root
    }
}