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
import m.ify.computersciencebouira.API.Module
import m.ify.computersciencebouira.Activities.ActivityPDFs
import m.ify.computersciencebouira.Helpers.ImageLoader
import m.ify.computersciencebouira.R

class AdapterModules(private val context: Context, private val data: MutableList<Module>) :
    RecyclerView.Adapter<AdapterModules.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterModules.ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.recycler_view_module, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: AdapterModules.ViewHolder, position: Int) {
        holder.title.text = data[position].name
        ImageLoader().loadImageToView(data[position].image.toString(),holder.image)
        holder.card.setOnClickListener {
            val intent = Intent(context,ActivityPDFs::class.java)
            intent.putExtra("module_id",data[position].id)
            intent.putExtra("module_name",data[position].name)
            intent.putExtra("module_desc",data[position].desc)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {


        val title: TextView = itemView.findViewById(R.id.moduleTitle)
        val image: ImageView = itemView.findViewById(R.id.moduleImage)
        val card: MaterialCardView = itemView.findViewById(R.id.moduleCard)


    }
}