package kr.us.us_android.feature.routine

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import kr.us.us_android.application.UsApplication
import kr.us.us_android.common.Constant
import kr.us.us_android.data.routine.AddRoutineRequest
import kr.us.us_android.data.routine.RoutineRequestManager
import kr.us.us_android.databinding.FragmentAddExerciseRouteBinding
import kr.us.us_android.util.shortToast

class AddExerciseRoutineFragment : Fragment() {

    private lateinit var binding: FragmentAddExerciseRouteBinding
    private lateinit var exerciseRoutine: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddExerciseRouteBinding.inflate(inflater, container, false)

        binding.add.setOnClickListener {
            addExerciseRoutineRequest()
        }

        return binding.root
    }

    private fun addExerciseRoutineRequest() {
        exerciseRoutine = binding.exerciseRoutine.text.toString().trim()

        val addRoutineRequest = AddRoutineRequest(exerciseRoutine)

        viewLifecycleOwner.lifecycleScope.launch {
            try {
                val token = "Bearer ${UsApplication.prefs.token}"
                val response = RoutineRequestManager.addExerciseRoutineRequest(token, addRoutineRequest)
                Log.d(Constant.TAG, "response.header : ${response.code()}")

                if (response.isSuccessful) {
                    context?.shortToast("운동 루틴 추가에 성공했습니다.")
                }
            } catch (e: Exception) {
                e.message
            }
        }
    }
}