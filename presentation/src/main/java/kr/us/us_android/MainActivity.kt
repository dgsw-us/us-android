package kr.us.us_android

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import kr.us.us_android.databinding.ActivityMainBinding

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

    private var recentPosition = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()
    }

    private fun initView() {

        bottomNavigationView = binding.bottomNav

        supportFragmentManager.beginTransaction()
            .replace(binding.mainFrameContainer.id, bottomNavFragments[0])
            .commit()

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