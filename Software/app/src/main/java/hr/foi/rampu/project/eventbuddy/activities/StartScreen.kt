package hr.foi.rampu.project.eventbuddy.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import hr.foi.rampu.project.eventbuddy.R
import hr.foi.rampu.project.eventbuddy.adapters.HomepageEventsAdapter
import hr.foi.rampu.project.eventbuddy.fragments.EditFragment
import hr.foi.rampu.project.eventbuddy.fragments.HomepageEventsFragment
import hr.foi.rampu.project.eventbuddy.fragments.MyEventsFragment


class StartScreen : AppCompatActivity(){
    lateinit var start_screen_nav: TabLayout
    lateinit var start_screen_page: ViewPager2
    lateinit var homepageAdapter: HomepageEventsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.start_screen)

        initializeHomepageEventsAdapter()
        connectViewPagerWithTabLayout()
    }

    private fun connectViewPagerWithTabLayout() {
        start_screen_nav = findViewById(R.id.tl_start_screen_nav)
        start_screen_page = findViewById(R.id.wp2_start_screen_page)

        start_screen_page.adapter = homepageAdapter

        TabLayoutMediator(start_screen_nav, start_screen_page) { tab, pos ->
            tab.setText(homepageAdapter.fragmentItems[pos].titleRes)
        }.attach()
    }

    private fun initializeHomepageEventsAdapter() {
        homepageAdapter = HomepageEventsAdapter(supportFragmentManager, lifecycle)
        fillAdapterWithFragments()
    }

    private fun fillAdapterWithFragments() {
        homepageAdapter.addFragment(
            HomepageEventsAdapter.FragmentItem(
                "Početna",
                HomepageEventsFragment::class
            )
        )
        homepageAdapter.addFragment(
            HomepageEventsAdapter.FragmentItem(
                "Moji događaji",
                MyEventsFragment::class
            )
        )
        homepageAdapter.addFragment(
            HomepageEventsAdapter.FragmentItem(
                "Profil ",
                EditFragment::class
            )
        )
    }
}