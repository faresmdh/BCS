package m.ify.bouiracomputerscience.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.marginEnd
import androidx.core.view.marginStart
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import m.ify.bouiracomputerscience.API.Playlist
import m.ify.computersciencebouira.API.PDF
import m.ify.computersciencebouira.Adapters.AdapterPDFs
import m.ify.computersciencebouira.Adapters.AdapterPlaylists
import m.ify.computersciencebouira.Helpers.StateSaver
import m.ify.computersciencebouira.databinding.FragmentCoursesBinding

class FragmentPlaylists(val list:MutableList<Playlist>, val type:String) : Fragment() {

    private lateinit var b: FragmentCoursesBinding
    private lateinit var stateSaver:StateSaver

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        b = FragmentCoursesBinding.inflate(inflater)

        stateSaver = StateSaver(requireContext())

        b.recyclerView.setPadding(12,0,12,0)

        b.recyclerView.layoutManager = GridLayoutManager(requireContext(),2,LinearLayoutManager.VERTICAL,false)
        b.recyclerView.adapter = AdapterPlaylists(requireContext(),list)


        return b.root
    }

}