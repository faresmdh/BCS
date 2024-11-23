package m.ify.computersciencebouira.Activities

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.ymg.pdf.viewer.util.FitPolicy
import m.ify.computersciencebouira.Helpers.StateSaver
import m.ify.computersciencebouira.databinding.ActivityPdfBinding
import java.io.File

class ActivityPDF : AppCompatActivity() {

    private lateinit var b:ActivityPdfBinding
    private lateinit var stateSaver: StateSaver


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = ActivityPdfBinding.inflate(layoutInflater)
        setContentView(b.root)

        stateSaver = StateSaver(this)

        val pdf = intent.getStringExtra("pdf")
        val title = intent.getStringExtra("title")
        val id = intent.getIntExtra("id",0)
        b.toolbarTitle.text = title
        b.backCV.setOnClickListener {
            onBackPressed()
        }
        val file = File(pdf.toString())
        b.pdfView.fromFile(file)
            .spacing(32)
            .pageFitPolicy(FitPolicy.BOTH)
            .defaultPage(stateSaver.getState("pdf"+id))
            .onPageChange { page, pageCount ->
                stateSaver.setState("pdf"+id,page)
                b.page.text = "${page+1}/$pageCount"
            }
            .load()

        b.page.text = "${b.pdfView.currentPage}/${b.pdfView.pageCount}"

        b.pdfView.minZoom = .5f
        b.pdfView.midZoom = .9f
        b.pdfView.maxZoom = 5f

    }
}