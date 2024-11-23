package m.ify.computersciencebouira.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import m.ify.computersciencebouira.databinding.FragmentOb1Binding

class FragmentOB1():Fragment() {

    private lateinit var b:FragmentOb1Binding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        b = FragmentOb1Binding.inflate(inflater)



        return b.root
    }

}