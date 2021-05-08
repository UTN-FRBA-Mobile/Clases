package ar.edu.utn.frba.mobile.clases.adapters

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import java.util.ArrayList

class ViewPagerAdapter(val fragments: List<Fragment>, parent: Fragment) : FragmentStateAdapter(parent) {

    override fun getItemCount(): Int =
        fragments.size

    override fun createFragment(position: Int): Fragment =
        fragments[position]
}