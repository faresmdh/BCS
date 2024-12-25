package m.ify.bouiracomputerscience.Fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.google.gson.Gson
import kotlinx.coroutines.launch
import m.ify.computersciencebouira.API.DB
import m.ify.computersciencebouira.Activities.ActivityMain
import m.ify.computersciencebouira.Adapters.AdapterBNV
import m.ify.computersciencebouira.Adapters.AdapterSemesters
import m.ify.computersciencebouira.Helpers.StateSaver
import m.ify.computersciencebouira.R
import m.ify.computersciencebouira.databinding.FragmentHomeBinding

class FragmentHome: Fragment() {

    private lateinit var b:FragmentHomeBinding
    private val db = DB()
    private lateinit var stateSaver: StateSaver
    private lateinit var level: String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        b = FragmentHomeBinding.inflate(inflater)

        stateSaver = StateSaver(requireActivity())
        level = stateSaver.getText("level").toString()

        b.levelTV.text = level

        b.refresh.setOnClickListener {
            b.loading.visibility = View.VISIBLE
            b.semViewPager.visibility = View.GONE
            getData(false)
        }


        checkData()


        b.logo.setOnClickListener {
            val activityMain = requireActivity() as ActivityMain
            activityMain.bnv.selectedItemId = R.id.bnv_profile
        }

        return b.root
    }

    private fun checkData() {
        val modules = stateSaver.getText("modules")
        val pdfs = stateSaver.getText("pdfs")
        val playlists = stateSaver.getText("playlists")
        if (modules == "null" || pdfs == "null"|| playlists == "null") {
            //data not downloaded yet (start loading)
            b.loading.visibility = View.VISIBLE
            b.semViewPager.visibility = View.GONE
            getData(true)
        } else {
            //data downloaded
            updateUI()
        }
    }

    fun updateUI() {
        b.semViewPager.adapter = AdapterSemesters(requireActivity().supportFragmentManager)
        b.tabLayout.setupWithViewPager(b.semViewPager)
        b.loading.visibility = View.GONE
        b.semViewPager.visibility = View.VISIBLE
    }

    private fun getData(iSFirstTime: Boolean) {
        lifecycleScope.launch {
            try {
                val modules = db.getModules()
                val pdfs = db.getPDFs()
                val playlists = db.getPlaylists()
                stateSaver.setText("modules", Gson().toJson(modules))
                stateSaver.setText("pdfs", Gson().toJson(pdfs))
                stateSaver.setText("playlists", Gson().toJson(playlists))
                updateUI()
            } catch (e: Exception) {
                Log.d("Fares error is here:", e.message.toString())
                if (iSFirstTime) {
                    //show error
                    Toast.makeText(requireActivity(), getString(R.string.restart), Toast.LENGTH_SHORT).show()
                }else{
                    Toast.makeText(requireActivity(), getString(R.string.error), Toast.LENGTH_SHORT).show()
                    updateUI()
                }
            }
        }
    }

}