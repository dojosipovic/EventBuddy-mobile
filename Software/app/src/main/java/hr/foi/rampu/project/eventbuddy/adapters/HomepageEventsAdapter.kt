package hr.foi.rampu.project.eventbuddy.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import kotlin.reflect.KClass

class HomepageEventsAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager, lifecycle) {

    data class FragmentItem(val titleRes: String, val fragmentClass: KClass<*>)
    val fragmentItems = ArrayList<FragmentItem>()

    override fun getItemCount(): Int {
        return fragmentItems.size
    }

    override fun createFragment(position: Int): Fragment {
        return fragmentItems[position].fragmentClass.java.getDeclaredConstructor().newInstance() as Fragment
    }

    fun addFragment(fragment: FragmentItem) {
        fragmentItems.add(fragment)
    }
}