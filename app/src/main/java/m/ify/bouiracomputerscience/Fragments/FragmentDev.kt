package m.ify.bouiracomputerscience.Fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.coroutines.launch
import m.ify.computersciencebouira.API.DB
import m.ify.computersciencebouira.Adapters.AdapterDevPosts
import m.ify.computersciencebouira.databinding.FragmentDevBinding

class FragmentDev:Fragment() {

    private lateinit var b :FragmentDevBinding
    private val db:DB = DB()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        b = FragmentDevBinding.inflate(inflater)

        getData()
        b.retryBtn.setOnClickListener { getData() }

        return b.root
    }

    private fun getData() {
        b.loading.visibility = View.VISIBLE
        b.devRV.visibility = View.GONE
        b.retryBtn.visibility = View.GONE
        lifecycleScope.launch {
            try {
                val posts = db.getDevPosts()
                b.devRV.layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false)
                b.devRV.adapter = AdapterDevPosts(requireContext(),posts)
                b.loading.visibility = View.GONE
                b.devRV.visibility = View.VISIBLE
            }catch (e:Exception){
                Log.d("Fares",e.message.toString())
                b.loading.visibility = View.GONE
                b.devRV.visibility = View.GONE
                b.retryBtn.visibility = View.VISIBLE
            }
        }
    }

}