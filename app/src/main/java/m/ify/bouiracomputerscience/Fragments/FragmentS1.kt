package m.ify.computersciencebouira.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.google.gson.Gson
import m.ify.computersciencebouira.API.Module
import m.ify.computersciencebouira.Adapters.AdapterModules
import m.ify.computersciencebouira.Helpers.StateSaver
import m.ify.computersciencebouira.databinding.FragmentS1Binding

class FragmentS1 : Fragment() {

    private lateinit var b:FragmentS1Binding
    private val SPAN_COUNT = 3

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        b = FragmentS1Binding.inflate(inflater)

        updateUI()

        return b.root
    }

    private fun updateUI() {
        val stateSaver =  StateSaver(requireContext())
        val modules = stateSaver.getText("modules")
        val level = stateSaver.getText("level").toString()
        val allModules = Gson().fromJson(modules, Array<Module>::class.java)
        val s1Modules = mutableListOf<Module>()
        for (mod in allModules) if (mod.level == level && mod.semestre == "S1") s1Modules.add(mod)
        b.recyclerView.layoutManager = GridLayoutManager(requireContext(),SPAN_COUNT,GridLayoutManager.VERTICAL,false)
        b.recyclerView.adapter = AdapterModules(requireContext(), s1Modules)
    }

}