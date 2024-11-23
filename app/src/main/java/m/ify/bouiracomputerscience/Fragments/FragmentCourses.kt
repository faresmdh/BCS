package m.ify.bouiracomputerscience.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import m.ify.computersciencebouira.API.PDF
import m.ify.computersciencebouira.Adapters.AdapterPDFs
import m.ify.computersciencebouira.Helpers.StateSaver
import m.ify.computersciencebouira.databinding.FragmentCoursesBinding

class FragmentCourses(val list:MutableList<PDF>,val type:String) : Fragment() {

    private lateinit var b: FragmentCoursesBinding
    private lateinit var stateSaver:StateSaver

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        b = FragmentCoursesBinding.inflate(inflater)

        stateSaver = StateSaver(requireContext())

        val finalList = mutableListOf<PDF>()

        for (pdf in list) if (pdf.type == type) finalList.add(pdf)
        b.recyclerView.layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false)
        b.recyclerView.adapter = AdapterPDFs(requireContext(),finalList)


        return b.root
    }

}