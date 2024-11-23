package m.ify.computersciencebouira.Adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import m.ify.computersciencebouira.Fragments.FragmentS1
import m.ify.computersciencebouira.Fragments.FragmentS2

class AdapterSemesters(fm: FragmentManager) : FragmentPagerAdapter(fm) {

    val fragments = mutableListOf<Fragment>(FragmentS1(),FragmentS2())
    val titles = mutableListOf<String>("S01","S02")

    override fun getCount(): Int {
        return fragments.size
    }

    override fun getItem(position: Int): Fragment {
        return fragments[position]
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return titles[position]
    }
}