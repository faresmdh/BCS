package m.ify.computersciencebouira.Adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import m.ify.bouiracomputerscience.Fragments.FragmentDev
import m.ify.bouiracomputerscience.Fragments.FragmentHome
import m.ify.bouiracomputerscience.Fragments.FragmentProfile
import m.ify.computersciencebouira.Fragments.FragmentS1
import m.ify.computersciencebouira.Fragments.FragmentS2

class AdapterBNV(fm: FragmentManager) : FragmentPagerAdapter(fm) {

    val fragments = mutableListOf<Fragment>(FragmentHome(),FragmentDev(),FragmentProfile())

    override fun getCount(): Int {
        return fragments.size
    }

    override fun getItem(position: Int): Fragment {
        return fragments[position]
    }

}