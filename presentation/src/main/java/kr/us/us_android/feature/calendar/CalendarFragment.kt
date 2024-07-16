package kr.us.us_android.feature.calendar

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import kr.us.us_android.R
import kr.us.us_android.databinding.FragmentCalendarBinding

class CalendarFragment : Fragment(R.layout.fragment_calendar) {

    private var _binding: FragmentCalendarBinding ?= null
    private lateinit var recyclerView: RecyclerView

    private val binding get() = _binding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCalendarBinding.inflate(inflater, container, false)

        val view = binding?.root

        initView(_binding!!)
        createData()

        return view
    }

    private fun initView(binding: FragmentCalendarBinding) {
        recyclerView = binding.calRecycler
        val position: Int = Int.MAX_VALUE / 2

        binding.calRecycler.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding.calRecycler.adapter = MonthAdapter()

        //item의 위치 지정
        binding.calRecycler.scrollToPosition(position)

        //항목씩 스크롤 지정
        val snap = PagerSnapHelper()
        snap.attachToRecyclerView(binding.calRecycler)
    }

    private fun createData() {
        binding?.calRecycler?.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}