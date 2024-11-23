package m.ify.computersciencebouira.Helpers

import android.app.DownloadManager
import android.content.Context
import android.content.Context.RECEIVER_EXPORTED
import android.content.Intent
import android.content.IntentFilter
import android.net.Uri
import android.os.Build
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.annotation.RequiresApi
import m.ify.computersciencebouira.API.PDF
import m.ify.computersciencebouira.Activities.ActivityPDF
import java.io.File

class FileDownloader(
    private val context: Context,
    private val pdf: PDF,
    private val dialogs: Dialogs,
    private val offlineIV: ImageView
) {

    private val downloadManager: DownloadManager = context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
    private val tmpPdfPath: String = File(context.getExternalFilesDir(null).toString() + "/.tmp.pdf").toString()

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    fun downloadWithProgressDialog() {
        val request = DownloadManager.Request(Uri.parse(pdf.url))
            .setTitle("Downloading PDF")
            .setDescription("Please wait...")
            .setDestinationUri(Uri.fromFile(File(tmpPdfPath)))

        val downloadId = downloadManager.enqueue(request)

        val downloadReceiver = DownloadReceiver(downloadId)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            context.registerReceiver(downloadReceiver, IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE), RECEIVER_EXPORTED)
        }else{
            context.registerReceiver(downloadReceiver, IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE))
        }
    }

    inner class DownloadReceiver(private val downloadId: Long) :
        android.content.BroadcastReceiver() {

        override fun onReceive(context: Context?, intent: Intent?) {
            if (intent?.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1) == downloadId) {
                val downloadQuery = DownloadManager.Query().setFilterById(downloadId)
                val cursor = downloadManager.query(downloadQuery)

                if (cursor.moveToFirst()) {
                    val statusColumnIndex = cursor.getColumnIndex("status") // Use the column name "status" for Android Q and later
                    val status = cursor.getInt(statusColumnIndex)

                    if (status == DownloadManager.STATUS_SUCCESSFUL) {
                        handleDownloadSuccess()
                    } else {
                        handleDownloadFailure()
                    }
                }
                dialogs.stopLoadingDialog()
                cursor.close()
                context?.unregisterReceiver(this)
            }
        }

        private fun handleDownloadSuccess() {
            // Move the downloaded file to the final path
            val tmpFile = File(tmpPdfPath)
            val finalFile = File(context.getExternalFilesDir(null).toString() + "/" + pdf.id + ".pdf")

            if (tmpFile.renameTo(finalFile)) {
                // File moved successfully
                offlineIV.visibility = View.VISIBLE
                openPdfFile()
            } else {
                Toast.makeText(context, "Failed to move the downloaded file", Toast.LENGTH_SHORT).show()
            }
        }

        private fun handleDownloadFailure() {
            Toast.makeText(context, "Download failed", Toast.LENGTH_SHORT).show()
        }

        private fun openPdfFile() {
            // Open the downloaded PDF file using an Intent
            val pdfPath = File(context.getExternalFilesDir(null), "/"+ pdf.id + ".pdf")
            val intent = Intent(context,ActivityPDF::class.java)
            intent.putExtra("pdf",pdfPath.toString())
            intent.putExtra("title",pdf.title)
            context.startActivity(intent)
        }
    }
}
