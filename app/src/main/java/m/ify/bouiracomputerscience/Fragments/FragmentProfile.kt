package m.ify.bouiracomputerscience.Fragments

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.google.gson.Gson
import m.ify.computersciencebouira.API.Student
import m.ify.computersciencebouira.Activities.ActivityLevel
import m.ify.computersciencebouira.Helpers.Dialogs
import m.ify.computersciencebouira.Helpers.ImageLoader
import m.ify.computersciencebouira.Helpers.StateSaver
import m.ify.computersciencebouira.databinding.FragmentProfileBinding


class FragmentProfile:Fragment() {

    private lateinit var b :FragmentProfileBinding
    private lateinit var stateSaver: StateSaver
    private lateinit var dialogs: Dialogs

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        b = FragmentProfileBinding.inflate(inflater)

        stateSaver = StateSaver(requireActivity())
        dialogs = Dialogs(requireActivity())

        val student = Gson().fromJson(stateSaver.getText("student"), Student::class.java)
        ImageLoader().loadAvatar(student.image!!,b.avatarIV)
        b.name.text = student.name
        b.email.text = student.email

        b.aboutCV.setOnClickListener {
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://bouira-computer-society.netlify.app/"))
            startActivity(browserIntent)
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
            startActivity(Intent(requireActivity(), ActivityLevel::class.java))
        }

        b.sourceCV.setOnClickListener {
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/faresmdh/BCS"))
            startActivity(browserIntent)
        }

        b.rateCV.setOnClickListener {
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=m.ify.computersciencebouira"))
            startActivity(browserIntent)
        }

        try {
            val pInfo = requireContext().packageManager.getPackageInfo(
                requireContext().packageName, 0
            )
            val version = pInfo.versionName
            b.version.text = "V $version"
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }


        return b.root
    }

}