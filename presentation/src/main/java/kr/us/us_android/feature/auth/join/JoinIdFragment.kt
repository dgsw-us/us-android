package kr.us.us_android.feature.auth.join

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kr.us.us_android.R
import kr.us.us_android.databinding.FragmentJoinIdBinding

class JoinIdFragment : Fragment() {

    private lateinit var binding: FragmentJoinIdBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentJoinIdBinding.inflate(inflater, container, false)

        return binding.root
    }
}