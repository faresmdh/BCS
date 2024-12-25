package m.ify.computersciencebouira.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView
import m.ify.bouiracomputerscience.API.DevPost
import m.ify.computersciencebouira.R
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter

class AdapterDevPosts(private val context: Context, private val data: List<DevPost>) :
    RecyclerView.Adapter<AdapterDevPosts.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterDevPosts.ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.recycler_view_dev, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: AdapterDevPosts.ViewHolder, position: Int) {
        holder.desc.text = data[position].description
        holder.date.text = getDate(data[position].created_at)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {


        val date: TextView = itemView.findViewById(R.id.dateTV)
        val desc: TextView = itemView.findViewById(R.id.descTV)
        val card: MaterialCardView = itemView.findViewById(R.id.card)


    }

    fun getDate(timestamptz: String): String {
        // Parse the ISO 8601 format string into an OffsetDateTime
        val dateTime = OffsetDateTime.parse(timestamptz)

        // Define the formatter for the desired output format
        val outputFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm")

        // Format the parsed date to the desired output format
        return dateTime.format(outputFormatter)
    }
}