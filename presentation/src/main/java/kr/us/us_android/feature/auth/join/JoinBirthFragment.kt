package kr.us.us_android.feature.auth.join

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import kr.us.us_android.R
import kr.us.us_android.databinding.FragmentJoinBirthBinding
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class JoinBirthFragment : Fragment() {

    private lateinit var binding: FragmentJoinBirthBinding
    private val joinViewModel: JoinViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentJoinBirthBinding.inflate(inflater, container, false)

        datePickerDefaultSettings()

        binding.btnContinue.setOnClickListener {
            val dateOfBirth = getSelectedDate()

            joinViewModel.birthdate.observe(viewLifecycleOwner) {
                getSelectedDate()
            }
            joinViewModel.setBirthdate(dateOfBirth)
            saveBirth(dateOfBirth)

            requireActivity().supportFragmentManager.beginTransaction()
                .setCustomAnimations(
                    R.anim.anim_slide_in_from_bottom,
                    R.anim.anim_fade_out_150,
                    R.anim.anim_fade_in_150,
                    R.anim.anim_fade_out_150
                )
                .replace(R.id.main_frame_container, JoinNameFragment())
                .addToBackStack(null)
                .commitAllowingStateLoss()
        }

        return binding.root
    }

    private fun datePickerDefaultSettings() {
        val datePicker = binding.dpSpinner
        datePicker.maxDate = System.currentTimeMillis() - 1000

        val calendar = Calendar.getInstance()
        calendar.set(2000, Calendar.JANUARY, 1)

        datePicker.init(
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        ) { view, year, monthOfYear, dayOfMonth ->
            // 날짜가 변경될 때 실행할 작업
            val dateOfBirth = getSelectedDate()
            joinViewModel.setBirthdate(dateOfBirth)
        }
    }

    private fun getSelectedDate(): String {
        val datePicker = binding.dpSpinner
        val day = datePicker.dayOfMonth
        val month = datePicker.month
        val year = datePicker.year

        val calendar = Calendar.getInstance()
        calendar.set(year, month, day)

        val format = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        return format.format(calendar.time)
    }

    private fun saveBirth(birth: String) {
        val sharedPref = requireActivity().getSharedPreferences("MyApp", Context.MODE_PRIVATE)
        with (sharedPref.edit()) {
            putString("birth", birth)
            commit()
        }
    }
}