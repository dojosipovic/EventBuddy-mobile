package hr.foi.rampu.project.eventbuddy.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import hr.foi.rampu.project.eventbuddy.R
import hr.foi.rampu.project.eventbuddy.adapters.EventsAdapter
import hr.foi.rampu.project.eventbuddy.database.EventsDao
import hr.foi.rampu.project.eventbuddy.helpers.LoggedInUser

class MyEventsUpcomingFragment : Fragment() {
    private lateinit var eventsDao: EventsDao
    private lateinit var recyclerView: RecyclerView
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_my_events_upcoming, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        eventsDao = EventsDao()
        recyclerView = view.findViewById(R.id.rv_upcoming_events)
        recyclerView.adapter = EventsAdapter(
            eventsDao
                .getUserSubscribedUpcomingEvents(LoggedInUser.user!!)
                .toMutableList()
        )
        recyclerView.layoutManager = LinearLayoutManager(view.context)
    }

    override fun onResume() {
        super.onResume()
        eventsDao = EventsDao()
        recyclerView = requireView().findViewById(R.id.rv_upcoming_events)
        recyclerView.adapter = EventsAdapter(
            eventsDao
                .getUserSubscribedUpcomingEvents(LoggedInUser.user!!)
                .toMutableList()
        )
        recyclerView.layoutManager = LinearLayoutManager(requireView().context)
    }
}