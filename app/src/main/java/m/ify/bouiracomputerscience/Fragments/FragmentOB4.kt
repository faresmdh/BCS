package m.ify.computersciencebouira.Fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import m.ify.computersciencebouira.Activities.ActivityLogin
import m.ify.computersciencebouira.Helpers.StateSaver
import m.ify.computersciencebouira.databinding.FragmentOb4Binding

class FragmentOB4():Fragment() {

    private lateinit var b: FragmentOb4Binding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        b = FragmentOb4Binding.inflate(inflater)

        b.startBtn.setOnClickListener {
            val stateSaver = StateSaver(requireContext())
            stateSaver.setState("ob screen",1)
            startActivity(Intent(requireContext(),ActivityLogin::class.java))
            requireActivity().finish()
        }

        return b.root
    }

}