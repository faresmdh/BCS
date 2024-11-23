package m.ify.computersciencebouira.Activities


import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.google.gson.Gson
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.auth.status.SessionStatus
import kotlinx.coroutines.launch
import m.ify.bouiracomputerscience.Activities.ActivityProfile
import m.ify.computersciencebouira.API.DB
import m.ify.computersciencebouira.Adapters.AdapterSemesters
import m.ify.computersciencebouira.Helpers.StateSaver
import m.ify.computersciencebouira.R
import m.ify.computersciencebouira.databinding.ActivityMainBinding

class ActivityMain : AppCompatActivity() {

    private lateinit var b: ActivityMainBinding
    private val db = DB()
    private lateinit var stateSaver: StateSaver
    private lateinit var level: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = ActivityMainBinding.inflate(layoutInflater)
        setContentView(b.root)

        stateSaver = StateSaver(this)
        level = stateSaver.getText("level").toString()
        b.levelTV.text = level

        b.refresh.setOnClickListener {
            stateSaver.setText("modules","null")
            stateSaver.setText("pdfs","null")
            checkData()
        }


        checkData()


        b.logo.setOnClickListener {
            startActivity(Intent(this, ActivityProfile::class.java))
        }

        lifecycleScope.launch {
            try {
                listenToAuth()
            } catch (e: Error) {
                Toast.makeText(this@ActivityMain, e.message.toString(), Toast.LENGTH_SHORT).show()
            }
        }

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

    private fun checkData() {
        val modules = stateSaver.getText("modules")
        val pdfs = stateSaver.getText("pdfs")
        if (modules == "null" || pdfs == "null") {
            //data not downloaded yet (start loading)
            b.loading.visibility = View.VISIBLE
            b.viewPager.visibility = View.GONE
            getData(true)
        } else {
            //data downloaded
            updateUI()
        }
    }

    fun updateUI() {
        b.viewPager.adapter = AdapterSemesters(supportFragmentManager)
        b.tabLayout.setupWithViewPager(b.viewPager)
        b.loading.visibility = View.GONE
        b.viewPager.visibility = View.VISIBLE
    }

    private fun getData(iSFirstTime: Boolean = false) {
        lifecycleScope.launch {
            try {
                val modules = db.getModules()
                val pdfs = db.getPDFs()
                stateSaver.setText("modules", Gson().toJson(modules))
                stateSaver.setText("pdfs", Gson().toJson(pdfs))
                updateUI()
            } catch (e: Exception) {
                Log.d("Fares error is here:", e.message.toString())
                if (iSFirstTime) {
                    //show error
                    Toast.makeText(this@ActivityMain, getString(R.string.restart), Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}