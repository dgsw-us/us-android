package kr.us.us_android.feature.auth.join

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import kr.us.us_android.R
import kr.us.us_android.databinding.FragmentJoinIdBinding
import kr.us.us_android.databinding.FragmentJoinPasswordBinding
import kr.us.us_android.util.shortToast
import java.util.regex.Pattern

class JoinPasswordFragment : Fragment() {

    private lateinit var binding: FragmentJoinPasswordBinding
    private val joinViewModel: JoinViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentJoinPasswordBinding.inflate(inflater, container, false)

        binding.next.setOnClickListener {
            checkPassword()
        }

        return binding.root
    }

    private fun checkPassword() {
        val password = binding.joinPasswordEdit.text.toString()
        val checkedPassword = binding.joinCheckPasswordEdit.text.toString()

        if (password.isEmpty()) {
            context?.shortToast("비밀번호가 비어있습니다")
        } else if (checkedPassword.isEmpty()) {
            context?.shortToast("비밀번호 확인이 비어있습니다")
        } else if (password!=checkedPassword){
            context?.shortToast("비밀번호 값이 다릅니다")
        } else {
            val isPasswordValid = passwordRegularExpression(password)
            Log.d("isPasswordValid", "${passwordRegularExpression(password)}")

            if (isPasswordValid) {
                joinViewModel.password.observe(viewLifecycleOwner) {
                    binding.joinCheckPasswordEdit.setText(
                        it
                    )
                }
                joinViewModel.setPassword(binding.joinCheckPasswordEdit.text.toString())

                requireActivity().supportFragmentManager.beginTransaction()
                    .setCustomAnimations(
                        R.anim.anim_slide_in_from_bottom,
                        R.anim.anim_fade_out_150,
                        R.anim.anim_fade_in_150,
                        R.anim.anim_fade_out_150
                    )
                    .replace(R.id.main_frame_container, JoinBirthFragment())
                    .addToBackStack(null)
                    .commitAllowingStateLoss()
            } else {
                context?.shortToast("비밀번호 형식이 잘못되었습니다.")
            }
        }
    }

    private fun passwordRegularExpression(password: String): Boolean {
        val passwordPattern = "^.*(?=^.{8,15}\$)(?=.*\\d)(?=.*[a-zA-Z])(?=.*[!@#\$%^&+=]).*\$"
        val pattern = Pattern.compile(passwordPattern)
        val matcher = pattern.matcher(password)
        return matcher.find()
    }
}