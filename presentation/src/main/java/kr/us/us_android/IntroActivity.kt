package kr.us.us_android

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.coroutines.*

class IntroActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intro)

        // Coroutine을 사용하여 지연 후 MainActivity로 전환
        CoroutineScope(Dispatchers.Main).launch {
            delay(2000) // 2초 지연
            startActivity(Intent(applicationContext, MainActivity::class.java))
            finish() // 현재 액티비티 종료
        }
    }

    override fun onPause() {
        super.onPause()
        finish()
    }
}
