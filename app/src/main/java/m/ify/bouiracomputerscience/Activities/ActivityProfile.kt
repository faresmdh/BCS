package m.ify.bouiracomputerscience.Activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.google.gson.Gson
import m.ify.computersciencebouira.API.Student
import m.ify.computersciencebouira.Activities.ActivityLevel
import m.ify.computersciencebouira.Helpers.Dialogs
import m.ify.computersciencebouira.Helpers.ImageLoader
import m.ify.computersciencebouira.Helpers.StateSaver
import m.ify.computersciencebouira.databinding.ActivityProfileBinding

class ActivityProfile : AppCompatActivity() {

    private lateinit var b:ActivityProfileBinding
    private lateinit var stateSaver: StateSaver
    private lateinit var dialogs: Dialogs

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(b.root)

        stateSaver = StateSaver(this)
        dialogs = Dialogs(this)

        val student = Gson().fromJson(stateSaver.getText("student"), Student::class.java)
        ImageLoader().loadAvatar(student.image!!,b.avatarIV)
        b.name.text = student.name
        b.email.text = student.email
        b.level.text = stateSaver.getText("level")

        b.backCV.setOnClickListener { finish() }

        b.aboutCV.setOnClickListener {
            startActivity(Intent(this,ActivityAbout::class.java))
        }

        b.nightCV.setOnClickListener {
            dialogs.startNightMode()
        }
        b.signoutCV.setOnClickListener {
            dialogs.startSignOut(lifecycleScope)
        }
        b.languageCV.setOnClickListener {
            dialogs.startLanguage()
        }
        b.passwordCV.setOnClickListener {
            dialogs.startPasswordDialog(lifecycleScope)
        }

        b.levelCV.setOnClickListener {
            startActivity(Intent(this,ActivityLevel::class.java))
        }

    }
}