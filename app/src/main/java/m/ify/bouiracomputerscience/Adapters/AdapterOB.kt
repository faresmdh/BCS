package m.ify.computersciencebouira.Adapters

import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import m.ify.computersciencebouira.Fragments.FragmentOB1
import m.ify.computersciencebouira.Fragments.FragmentOB2
import m.ify.computersciencebouira.Fragments.FragmentOB3
import m.ify.computersciencebouira.Fragments.FragmentOB4

class AdapterOB(val fm: FragmentManager): FragmentPagerAdapter(fm) {

    private var fragments = listOf(FragmentOB1(), FragmentOB2(), FragmentOB3(), FragmentOB4())

    override fun getCount(): Int {
        return fragments.size
    }

    override fun getItem(position: Int): Fragment {
        return fragments[position]
    }

}