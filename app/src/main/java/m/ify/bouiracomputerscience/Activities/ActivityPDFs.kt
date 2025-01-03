package m.ify.computersciencebouira.Activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import m.ify.bouiracomputerscience.API.Playlist
import m.ify.bouiracomputerscience.Adapters.AdapterType
import m.ify.computersciencebouira.API.PDF
import m.ify.computersciencebouira.Adapters.AdapterPDFs
import m.ify.computersciencebouira.Helpers.StateSaver
import m.ify.computersciencebouira.R
import m.ify.computersciencebouira.databinding.ActivityPdfsBinding

class ActivityPDFs : AppCompatActivity() {

    private lateinit var b:ActivityPdfsBinding
    private lateinit var stateSaver: StateSaver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = ActivityPdfsBinding.inflate(layoutInflater)
        setContentView(b.root)

        stateSaver = StateSaver(this)

        val module_id = intent.getIntExtra("module_id",0)
        val module_name = intent.getStringExtra("module_name")
        val module_desc = intent.getStringExtra("module_desc")

        b.backCV.setOnClickListener { finish() }
        b.toolbarTitle.text = module_name
        b.moduleTV.text = module_desc

        val pdfs = Gson().fromJson(stateSaver.getText("pdfs"),Array<PDF>::class.java)
        val playlists = Gson().fromJson(stateSaver.getText("playlists"),Array<Playlist>::class.java)
        val finalPdfList = mutableListOf<PDF>()
        val finalPlaylistsList = mutableListOf<Playlist>()
        for (pdf in pdfs) if (pdf.module == module_id) finalPdfList.add(pdf)
        for (pl in playlists) if (pl.module == module_id) finalPlaylistsList.add(pl)
        var tp = false
        var td = false
        var exam = false
        var livres = false
        var pl = false
        for (pdf in finalPdfList) if (pdf.type == "TP") {
            tp = true
            break
        }
        for (pdf in finalPdfList) if (pdf.type == "TD") {
            td = true
            break
        }
        for (pdf in finalPdfList) if (pdf.type == "Tests/Examens") {
            exam = true
            break
        }
        for (pdf in finalPdfList) if (pdf.type == "Livres") {
            livres = true
            break
        }
        if (finalPlaylistsList.isNotEmpty()) pl = true


        val adapter = AdapterType(
            pdfList = finalPdfList,
            playlist = finalPlaylistsList,
            fm = supportFragmentManager,
            td = td,
            tp = tp,
            exam = exam,
            books = livres,
            playlists = pl
        )
        b.viewPager.adapter = adapter
        b.tabLayout.setupWithViewPager(b.viewPager)

    }
}