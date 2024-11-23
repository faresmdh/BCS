package m.ify.computersciencebouira.Activities

import android.content.Intent
import android.content.res.Configuration
import android.os.Build
import android.os.Bundle
import android.view.WindowInsets
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.splashscreen.SplashScreen
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.lifecycleScope
import androidx.viewpager.widget.ViewPager
import io.github.jan.supabase.auth.auth
import kotlinx.coroutines.launch
import m.ify.computersciencebouira.API.DB
import m.ify.computersciencebouira.Adapters.AdapterOB
import m.ify.computersciencebouira.Helpers.StateSaver
import m.ify.computersciencebouira.R
import m.ify.computersciencebouira.databinding.ActivityOnBoardingBinding
import java.util.Locale

class ActivityOnBoarding : AppCompatActivity() {

    private lateinit var b:ActivityOnBoardingBinding
    private lateinit var splashScreen: SplashScreen
    private lateinit var stateSaver: StateSaver

    override fun onCreate(savedInstanceState: Bundle?) {
        @Suppress("DEPRECATION")
        window.setFlags(
            WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
            WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
        )

        splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)
        b = ActivityOnBoardingBinding.inflate(layoutInflater)
        setContentView(b.root)

        splashScreen.setKeepOnScreenCondition { true }

        stateSaver = StateSaver(this)

        //check night mode and language
        when(stateSaver.getState("lang")){
            0 -> changeAppLanguage("null")
            1 -> changeAppLanguage("en")
            2 -> changeAppLanguage("fr")
            3 -> changeAppLanguage("ar")
        }
        when(stateSaver.getState("night")){
            0 -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
            1 -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            2 -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }

        val obState = stateSaver.getState("ob screen")

        if (obState == 0){
            //first time enter app
            val adapter = AdapterOB(supportFragmentManager)
            b.pager.adapter = adapter
            b.pager.offscreenPageLimit = 2
            b.pager.setButtonDrawable(R.drawable.ic_swipe_dark)
            b.pager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
                override fun onPageScrolled(
                    position: Int,
                    positionOffset: Float,
                    positionOffsetPixels: Int
                ) {}

                override fun onPageSelected(position: Int) {
                    if (position == 0 || position == 2) b.pager.setButtonDrawable(R.drawable.ic_swipe_dark)
                    else b.pager.setButtonDrawable(R.drawable.ic_swipe_light)
                }

                override fun onPageScrollStateChanged(state: Int) {}

            })
            splashScreen.setKeepOnScreenCondition{false}
        }else{
            //already passed
            val student = stateSaver.getText("student")
            val level = stateSaver.getText("level")
            if (student == "null"){
                val i = Intent(this@ActivityOnBoarding,ActivityLogin::class.java)
                startActivity(i)
            }else if (level == "null"){
                val i = Intent(this@ActivityOnBoarding,ActivityLevel::class.java)
                startActivity(i)
            }else{
                val i = Intent(this@ActivityOnBoarding,ActivityMain::class.java)
                startActivity(i)
            }
            finish()

        }


    }

    private fun changeAppLanguage(languageCode: String) {
        val locale: Locale = if (languageCode == "null"){
            Locale.getDefault()
        }else{
            Locale(languageCode)
        }
        Locale.setDefault(locale)

        val config = Configuration()
        config.setLocale(locale)

        // Apply the new configuration
        resources.updateConfiguration(config, resources.displayMetrics)
    }
}