package m.ify.computersciencebouira.Activities

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import com.itextpdf.kernel.colors.DeviceGray
import com.itextpdf.kernel.geom.PageSize
import com.itextpdf.kernel.pdf.PdfDocument
import com.itextpdf.kernel.pdf.PdfReader
import com.itextpdf.kernel.pdf.PdfWriter
import com.itextpdf.kernel.pdf.canvas.PdfCanvas
import com.itextpdf.layout.Document
import com.itextpdf.layout.element.Paragraph
import com.itextpdf.layout.properties.TextAlignment
import com.itextpdf.layout.properties.VerticalAlignment
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
        b.upBtn.setOnClickListener {
            b.pdfView.jumpTo(0)
        }
        val file = File(pdf.toString())
        b.pdfView.fromFile(file)
            .spacing(32)
            .pageFitPolicy(FitPolicy.BOTH)
            .defaultPage(0)
            .onPageChange { page, pageCount ->
                stateSaver.setState("pdf"+id,page)
                b.page.text = "${page+1}/$pageCount"
                if (page > 5) b.upBtn.visibility = View.VISIBLE
                else b.upBtn.visibility = View.GONE
            }
            .load()

        b.page.text = "${b.pdfView.currentPage}/${b.pdfView.pageCount}"

        b.pdfView.minZoom = .5f
        b.pdfView.midZoom = .9f
        b.pdfView.maxZoom = 3f

        b.shareCV.setOnClickListener {
            val sharedPDF = File(getExternalFilesDir(null), "/${title}(BCS).pdf")
            addWatermarkToPdf(file.path,sharedPDF.path,"Shared from BCS app\nAvailable in google play store")
        }

    }

    fun addWatermarkToPdf(inputPdfPath: String, outputPdfPath: String, watermarkText: String) {
        try {
            // Load the PDF
            val pdfReader = PdfReader(inputPdfPath)
            val pdfWriter = PdfWriter(outputPdfPath)
            val pdfDocument = PdfDocument(pdfReader, pdfWriter)

            // Get total number of pages
            val totalPages = pdfDocument.numberOfPages

            // Iterate through all pages
            for (i in 1..totalPages) {
                val page = pdfDocument.getPage(i)

                // Get the page dimensions
                val pageSize = page.pageSize
                val x = pageSize.width / 2
                val y = pageSize.height / 2

                // Create a Paragraph for the watermark
                val paragraph = Paragraph(watermarkText)
                    .setFontSize(20f)
                    .setFontColor(DeviceGray(0.75f))
                    .setTextAlignment(TextAlignment.CENTER)
                    .setRotationAngle(0f)
                    .setOpacity(.65f)

                // Add the watermark to the page
                PdfCanvas(page)
                val documentCanvas = Document(pdfDocument, PageSize.A4)
                documentCanvas.showTextAligned(paragraph,x,y,i,TextAlignment.CENTER,VerticalAlignment.MIDDLE,(Math.PI /4).toFloat())
            }

            // Close the document
            pdfDocument.close()
            sharePdfFile(outputPdfPath)
        } catch (e: Exception) {
            e.printStackTrace()
            sharePdfFile(filePath = inputPdfPath)
        }


    }

    fun sharePdfFile(filePath: String) {
        val file = File(filePath)
        if (file.exists()) {
            // Get URI using FileProvider
            Log.d("Fares log", "starting")
            val uri: Uri = FileProvider.getUriForFile(
                this,
                "${applicationContext.packageName}.fileprovider",
                file
            )
            Log.d("Fares log", "Success1")

            // Create share intent
            val shareIntent = Intent(Intent.ACTION_SEND).apply {
                type = "application/pdf"
                putExtra(Intent.EXTRA_STREAM, uri)
                addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            }

            Log.d("Fares log", "Success2")

            // Start share activity
            startActivity(Intent.createChooser(shareIntent, "Share this PDF"))

            Log.d("Fares log", "Success2")
        } else {
            println("File not found!")
        }
    }



}