package m.ify.bouiracomputerscience.Activities

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import m.ify.bouiracomputerscience.AppConst
import m.ify.computersciencebouira.databinding.ActivityAboutBinding

class ActivityAbout : AppCompatActivity() {


    private lateinit var b:ActivityAboutBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = ActivityAboutBinding.inflate(layoutInflater)
        setContentView(b.root)

        //Handle clicks
        b.backCV.setOnClickListener {
            finish()
        }
        b.sourCodeBtn.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(AppConst.SOURCE_CODE)
            startActivity(intent)
        }
        b.fbBtn.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse("fb://facewebmodal/f?href=${AppConst.FACEBOOK}")
            if (intent.resolveActivity(packageManager) != null) {
                startActivity(intent)
            } else {
                intent.data = Uri.parse(AppConst.FACEBOOK)
                startActivity(intent)
            }
        }
        b.instagramBtn.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse("http://instagram.com/_u/" + AppConst.INSTAGRAM.substring(AppConst.INSTAGRAM.lastIndexOf("/") + 1))
            if (intent.resolveActivity(packageManager) != null) {
                startActivity(intent)
            } else {
                intent.data = Uri.parse(AppConst.INSTAGRAM)
                startActivity(intent)
            }
        }
        b.githubBtn.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(AppConst.GITHUB)
            if (intent.resolveActivity(packageManager) != null) {
                startActivity(intent)
            } else {
                intent.data = Uri.parse(AppConst.GITHUB)
                startActivity(intent)
            }
        }
        b.xBtn.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(AppConst.X)
            if (intent.resolveActivity(packageManager) != null) {
                startActivity(intent)
            } else {
                intent.data = Uri.parse(AppConst.X)
                startActivity(intent)
            }
        }
        b.youtubeBtn.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(AppConst.YOUTUBE)
            if (intent.resolveActivity(packageManager) != null) {
                startActivity(intent)
            } else {
                intent.data = Uri.parse(AppConst.YOUTUBE)
                startActivity(intent)
            }
        }

    }
}