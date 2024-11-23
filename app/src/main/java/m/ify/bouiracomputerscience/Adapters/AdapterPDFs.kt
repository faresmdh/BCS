package m.ify.computersciencebouira.Adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView
import m.ify.computersciencebouira.API.PDF
import m.ify.computersciencebouira.Activities.ActivityPDF
import m.ify.computersciencebouira.Helpers.Dialogs
import m.ify.computersciencebouira.Helpers.FileDownloader
import m.ify.computersciencebouira.Helpers.NetworkChecker
import m.ify.computersciencebouira.R
import java.io.File

class AdapterPDFs(private val context: Context, private val data: MutableList<PDF>) :
    RecyclerView.Adapter<AdapterPDFs.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterPDFs.ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.recycler_view_pdf, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: AdapterPDFs.ViewHolder, position: Int) {
        holder.title.text = data[position].title
        val pdfPath = File(context.getExternalFilesDir(null), "/"+data[position].id.toString() + ".pdf")
        holder.offlineIV.visibility = if (pdfPath.exists()) View.VISIBLE else View.GONE
        holder.card.setOnClickListener {
            if (pdfPath.exists()){
                val intent = Intent(context,ActivityPDF::class.java)
                intent.putExtra("pdf",pdfPath.toString())
                intent.putExtra("id",data[position].id)
                intent.putExtra("title",data[position].title)
                context.startActivity(intent)
            }else{
                if (NetworkChecker(context).isConnectedWithDialog()){
                    downloadPDF(data[position],holder.offlineIV)
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }

    private fun downloadPDF(pdf: PDF, offlineIV: ImageView) {
        val dialogs = Dialogs(context)
        dialogs.startLoadingDialog()
        FileDownloader(
            context,
            pdf,
            dialogs,
            offlineIV
        ).downloadWithProgressDialog()
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {


        val title: TextView = itemView.findViewById(R.id.title)
        val offlineIV: ImageView = itemView.findViewById(R.id.offlineIV)
        val card: MaterialCardView = itemView.findViewById(R.id.card)


    }
}