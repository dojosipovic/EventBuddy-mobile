package hr.foi.rampu.project.eventbuddy.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import hr.foi.rampu.project.eventbuddy.R
import hr.foi.rampu.project.eventbuddy.adapters.HomepageEventsAdapter
import hr.foi.rampu.project.eventbuddy.helpers.LoggedInUser

class MyEventsFragment : Fragment() {
    lateinit var my_events_nav: TabLayout
    lateinit var my_events_page: ViewPager2
    lateinit var my_events_adapter: HomepageEventsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_my_events, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initializeNavigationAdapter()
        connectViewPagerWithTabLayout()
    }

    private fun connectViewPagerWithTabLayout() {
        my_events_nav = requireView().findViewById(R.id.tl_my_events_nav)
        my_events_page = requireView().findViewById(R.id.vp2_my_events_page)

        my_events_page.adapter = my_events_adapter

        TabLayoutMediator(my_events_nav, my_events_page) { tab, pos ->
            tab.setText(my_events_adapter.fragmentItems[pos].titleRes)
        }.attach()
    }

    private fun initializeNavigationAdapter() {
        my_events_adapter = HomepageEventsAdapter(childFragmentManager, lifecycle)
        fillAdapterWithFragments()
    }

    private fun fillAdapterWithFragments() {
        my_events_adapter.addFragment(
            HomepageEventsAdapter.FragmentItem(
                "Nadolazeći",
                MyEventsUpcomingFragment::class
            )
        )
        my_events_adapter.addFragment(
            HomepageEventsAdapter.FragmentItem(
                "Povijest",
                MyEventsHistoryFragment::class
            )
        )
        if (LoggedInUser.user!!.IsOrganizer()) {
            my_events_adapter.addFragment(
                HomepageEventsAdapter.FragmentItem(
                    "Vlastiti",
                    MyEventsCreateEventFragment::class
                )
            )
        }
    }
}