package m.ify.computersciencebouira.Activities


import android.content.Intent
import android.content.res.Resources
import android.os.Bundle
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.setPadding
import androidx.lifecycle.lifecycleScope
import com.google.android.material.bottomnavigation.BottomNavigationView
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.auth.status.SessionStatus
import kotlinx.coroutines.launch
import m.ify.computersciencebouira.API.DB
import m.ify.computersciencebouira.Adapters.AdapterBNV
import m.ify.computersciencebouira.Helpers.StateSaver
import m.ify.computersciencebouira.R
import m.ify.computersciencebouira.databinding.ActivityMainBinding
import org.bouncycastle.crypto.params.Blake3Parameters.context


class ActivityMain : AppCompatActivity() {

    private lateinit var b: ActivityMainBinding
    private val db = DB()
    private lateinit var stateSaver: StateSaver
    lateinit var bnv:BottomNavigationView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = ActivityMainBinding.inflate(layoutInflater)
        window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        setContentView(b.root)

        stateSaver = StateSaver(this)
        bnv = b.bnv
        b.viewPager.adapter = AdapterBNV(this.supportFragmentManager)

        adjustBottomNavigationPadding(bnv)

        lifecycleScope.launch {
            try {
                listenToAuth()
            } catch (e: Error) {
                Toast.makeText(this@ActivityMain, e.message.toString(), Toast.LENGTH_SHORT).show()
            }
        }

        b.bnv.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.bnv_home -> {
                    b.viewPager.setCurrentItem(0,true)
                    true
                }
                R.id.bnv_dev -> {
                    b.viewPager.setCurrentItem(1,true)
                    true
                }
                R.id.bnv_profile -> {
                    b.viewPager.setCurrentItem(2,true)
                    true
                }
                else -> false
            }
        }

        b.viewPager.offscreenPageLimit = 3



    }

    private suspend fun listenToAuth() {
        db.supabase.auth.sessionStatus.collect {
            when (it) {
                is SessionStatus.Authenticated -> {}
                SessionStatus.Initializing -> {}
                is SessionStatus.RefreshFailure -> {}
                is SessionStatus.NotAuthenticated -> {
                    stateSaver.setText("student", "null")
                    val intent = Intent(this@ActivityMain, ActivityLogin::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                    startActivity(intent)
                    finish()
                }
            }
        }
    }


    fun isGestEnabled(): Boolean {
        val resources: Resources = getResources()
        val resourceId =
            resources.getIdentifier("config_navBarInteractionMode", "integer", "android")
        return resources.getInteger(resourceId) == 2
    }

    fun adjustBottomNavigationPadding(bnv: BottomNavigationView) {
        ViewCompat.setOnApplyWindowInsetsListener(bnv) { view, insets ->
            val bottomInset = insets.systemGestureInsets.bottom
            // Adjust the padding to account for gesture navigation if it's enabled
            if (isGestEnabled()) {
                view.setPadding(0, 0, 0, 0)
            } else {
                view.setPadding(0, 0, 0, bottomInset)
            }
            insets
        }
    }

}