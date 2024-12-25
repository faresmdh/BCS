package m.ify.computersciencebouira.Adapters

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView
import m.ify.bouiracomputerscience.API.Playlist
import m.ify.bouiracomputerscience.AppConst
import m.ify.computersciencebouira.API.PDF
import m.ify.computersciencebouira.Activities.ActivityPDF
import m.ify.computersciencebouira.Helpers.Dialogs
import m.ify.computersciencebouira.Helpers.FileDownloader
import m.ify.computersciencebouira.Helpers.NetworkChecker
import m.ify.computersciencebouira.R
import java.io.File

class AdapterPlaylists(private val context: Context, private val data: MutableList<Playlist>) :
    RecyclerView.Adapter<AdapterPlaylists.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterPlaylists.ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.recycler_view_playlist, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: AdapterPlaylists.ViewHolder, position: Int) {
        holder.title.text = data[position].title
        holder.card.setOnClickListener {
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(data[position].link))
            context.startActivity(browserIntent)
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {


        val title: TextView = itemView.findViewById(R.id.title)
        val card: MaterialCardView = itemView.findViewById(R.id.card)


    }
}