package kr.us.us_android

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.google.android.material.bottomnavigation.BottomNavigationView
import kr.us.us_android.application.PreferenceManager
import kr.us.us_android.application.UsApplication
import kr.us.us_android.application.UserPrefs
import kr.us.us_android.databinding.ActivityMainBinding
import kr.us.us_android.feature.auth.login.LoginFragment
import kr.us.us_android.feature.community.CommunityFragment
import kr.us.us_android.feature.calendar.CalendarFragment
import kr.us.us_android.feature.home.HomeFragment
import kr.us.us_android.feature.my.MyFragment
import kr.us.us_android.feature.ricerecommend.RiceRecommendFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var bottomNavigationView: BottomNavigationView

    private val bottomNavFragments = listOf(
        RiceRecommendFragment(),
        CalendarFragment(),
        HomeFragment(),
        CommunityFragment(),
        MyFragment()
    )

    private var recentPosition = 2

    private val CHANNEL_ID = "default_channel_id"
    private val NOTIFICATION_ID = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (UsApplication.prefs.token==null) {
            navigateToLogin()
            return
        }

        if (!UserPrefs.isInitialized) {
            Log.d("MainActivity", "UserPrefs not initialized, initializing now")
            UserPrefs.init(applicationContext)
        }

        AlarmReceiver.setExactAlarm(this)

        if (!hasNotificationPermission()) {
            requestNotificationPermission()
        } else {
            createNotificationChannel()
            sendNotification()
        }

        initView()
    }

    private fun initView() {

        bottomNavigationView = binding.bottomNav

        supportFragmentManager.beginTransaction()
            .replace(binding.mainFrameContainer.id, bottomNavFragments[2])
            .commit()

        bottomNavigationView.selectedItemId = R.id.homeFragment

        bottomNavigationView.setOnItemSelectedListener {
            val transaction = supportFragmentManager.beginTransaction()
            when (it.itemId) {
                R.id.riceFragment -> {
                    transaction.setCustomAnimations(
                        R.anim.anim_slide_in_from_left_fade_in,
                        R.anim.anim_fade_out_200
                    )
                    transaction.replace(binding.mainFrameContainer.id, bottomNavFragments[0])
                    transaction.commit()
                    recentPosition = 0
                    return@setOnItemSelectedListener true
                }
                R.id.calendarFragment -> {
                    if (recentPosition < 1) {
                        transaction.setCustomAnimations(
                            R.anim.anim_slide_in_from_right_fade_in,
                            R.anim.anim_fade_out_200
                        )
                    } else {
                        transaction.setCustomAnimations(
                            R.anim.anim_slide_in_from_left_fade_in,
                            R.anim.anim_fade_out_200
                        )
                    }
                    transaction.replace(binding.mainFrameContainer.id, bottomNavFragments[1])
                    transaction.commit()
                    recentPosition = 1
                    return@setOnItemSelectedListener true
                }
                R.id.homeFragment -> {
                    if (recentPosition < 2) {
                        transaction.setCustomAnimations(
                            R.anim.anim_slide_in_from_right_fade_in,
                            R.anim.anim_fade_out_200
                        )
                    } else {
                        transaction.setCustomAnimations(
                            R.anim.anim_slide_in_from_left_fade_in,
                            R.anim.anim_fade_out_200
                        )
                    }
                    transaction.replace(binding.mainFrameContainer.id, bottomNavFragments[2])
                    transaction.commit()
                    recentPosition = 2
                    return@setOnItemSelectedListener true
                }
                R.id.communityFragment -> {
                    if (recentPosition < 3) {
                        transaction.setCustomAnimations(
                            R.anim.anim_slide_in_from_right_fade_in,
                            R.anim.anim_fade_out_200
                        )
                    } else {
                        transaction.setCustomAnimations(
                            R.anim.anim_slide_in_from_left_fade_in,
                            R.anim.anim_fade_out_200
                        )
                    }
                    transaction.replace(binding.mainFrameContainer.id, bottomNavFragments[3])
                    transaction.commit()
                    recentPosition = 3
                    return@setOnItemSelectedListener true
                }
                R.id.myFragment -> {
                    transaction.setCustomAnimations(
                        R.anim.anim_slide_in_from_right_fade_in,
                        R.anim.anim_fade_out_200
                    )
                    transaction.replace(binding.mainFrameContainer.id, bottomNavFragments[4])
                    transaction.commit()
                    recentPosition = 4
                    return@setOnItemSelectedListener true
                }
            }
            return@setOnItemSelectedListener false
        }
    }

    private fun hasNotificationPermission(): Boolean {
        return ActivityCompat.checkSelfPermission(this, Manifest.permission.VIBRATE) == PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.USE_FULL_SCREEN_INTENT) == PackageManager.PERMISSION_GRANTED
    }

    private fun requestNotificationPermission() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(
                Manifest.permission.VIBRATE,
                Manifest.permission.USE_FULL_SCREEN_INTENT
            ),
            PERMISSION_REQUEST_CODE
        )
    }

    private fun createNotificationChannel() {
        // Android 8.0 (API 레벨 26) 이상에서는 NotificationChannel을 설정해야 합니다.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "Default Channel"
            val descriptionText = "기본 알림 채널"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
                enableLights(true)
                lightColor = Color.RED
            }
            // 시스템에 채널을 등록합니다.
            val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    private fun sendNotification() {
        val builder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_notification)
            .setContentTitle("앱 시작 알림")
            .setContentText("환영합니다! 앱을 시작하였습니다.")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        // 알림을 표시합니다.
        with(NotificationManagerCompat.from(this)) {
            if (ActivityCompat.checkSelfPermission(
                    this@MainActivity,
                    Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return
            }
            notify(NOTIFICATION_ID, builder.build())
        }
    }

    companion object {
        private const val PERMISSION_REQUEST_CODE = 1001
    }

    private fun navigateToLogin() {
        supportFragmentManager.beginTransaction()
            .replace(binding.mainFrameContainer.id, LoginFragment())
            .commit()
    }
}