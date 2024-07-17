package kr.us.us_android.feature.auth.join

import android.content.Context
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
import kr.us.us_android.databinding.FragmentJoinEmailBinding
import kr.us.us_android.util.shortToast
import java.util.regex.Pattern

class JoinEmailFragment : Fragment() {

    private lateinit var binding: FragmentJoinEmailBinding
    private val joinViewModel: JoinViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentJoinEmailBinding.inflate(inflater, container, false)

        binding.next.setOnClickListener {
            checkEmail()
        }

        return binding.root
    }

    private fun checkEmail() {
        val email = binding.emailEditText.text.toString().trim()

        if (email.isEmpty()) {
            context?.shortToast("이메일이 비어있습니다")
        } else {
            val isEmailValid = emailRegularExpression(email)
            Log.d("isEmailValid", "${emailRegularExpression(email)}")

            if (isEmailValid) {
                joinViewModel.password.observe(viewLifecycleOwner) {
                    binding.emailEditText.setText(
                        it
                    )
                }
                joinViewModel.setEmail(email)
                saveEmail(email)

                requireActivity().supportFragmentManager.beginTransaction()
                    .setCustomAnimations(
                        R.anim.anim_slide_in_from_bottom,
                        R.anim.anim_fade_out_150,
                        R.anim.anim_fade_in_150,
                        R.anim.anim_fade_out_150
                    )
                    .replace(R.id.main_frame_container, JoinIdFragment())
                    .addToBackStack(null)
                    .commitAllowingStateLoss()

            } else {
                context?.shortToast("이메일 형식이 잘못되었습니다")
            }
        }
    }

    private fun emailRegularExpression(email: String): Boolean {
        val emailPattern = "^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}\$"
        val pattern = Pattern.compile(emailPattern)
        val matcher = pattern.matcher(email)
        return matcher.find()
    }

    private fun saveEmail(email: String) {
        val sharedPref = requireActivity().getSharedPreferences("MyApp", Context.MODE_PRIVATE)
        with (sharedPref.edit()) {
            putString("email", email)
            commit()
        }
    }
}