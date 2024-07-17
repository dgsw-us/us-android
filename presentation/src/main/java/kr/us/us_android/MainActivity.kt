package kr.us.us_android

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import kr.us.us_android.application.PreferenceManager
import kr.us.us_android.application.UsApplication
import kr.us.us_android.databinding.ActivityMainBinding
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        UsApplication.prefs = PreferenceManager(application)

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
}