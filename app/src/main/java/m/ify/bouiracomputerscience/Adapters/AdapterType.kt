package m.ify.bouiracomputerscience.Adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import m.ify.bouiracomputerscience.Fragments.FragmentCourses
import m.ify.computersciencebouira.API.PDF

class AdapterType(list:MutableList<PDF>,fm: FragmentManager,tp:Boolean?=true,td:Boolean?=true,exam:Boolean?=true,books:Boolean?=true) : FragmentPagerAdapter(fm) {

    val fragments = mutableListOf<Fragment>()
    val titles = mutableListOf<String>()

    init {
        fragments.add(FragmentCourses(list,"Cours"))
        if (td == true) fragments.add(FragmentCourses(list,"TD"))
        if (tp == true) fragments.add(FragmentCourses(list,"TP"))
        if (exam == true) fragments.add(FragmentCourses(list,"Tests et examens"))
        if (books == true) fragments.add(FragmentCourses(list,"Livres"))

        titles.add("Cours")
        if (td == true) titles.add("TD")
        if (tp == true) titles.add("TP")
        if (exam == true) titles.add("Tests et examens")
        if (books == true) titles.add("Livres")
    }

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