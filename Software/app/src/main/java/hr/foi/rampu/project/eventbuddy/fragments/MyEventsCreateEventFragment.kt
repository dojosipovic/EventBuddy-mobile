package hr.foi.rampu.project.eventbuddy.fragments

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import hr.foi.rampu.project.eventbuddy.R
import hr.foi.rampu.project.eventbuddy.adapters.EventsAdapter
import hr.foi.rampu.project.eventbuddy.database.EventsDao
import hr.foi.rampu.project.eventbuddy.helpers.LoggedInUser
import hr.foi.rampu.project.eventbuddy.helpers.MockDataLoader
import hr.foi.rampu.project.eventbuddy.helpers.NewEventDialogHelper

class MyEventsCreateEventFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var btnCreateNewEvent: FloatingActionButton
    private lateinit var eventsDao: EventsDao
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_my_events_create_event, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btnCreateNewEvent = view.findViewById(R.id.fab_my_events_create_event)
        btnCreateNewEvent.setOnClickListener {
            showDialog()
        }

        showEvents()
    }

    fun showDialog(){
        val newTaskDialogView = LayoutInflater
            .from(context)
            .inflate(R.layout.new_event_dialog, null)

        val dialogHelper = NewEventDialogHelper(newTaskDialogView)
        AlertDialog.Builder(context)
            .setView(newTaskDialogView)
            .setTitle("Kreiraj novi događaj")
            .setPositiveButton("Kreiraj") { _, _ ->
                val newEvent = dialogHelper.buildEvent()
                Log.d("TAG", newEvent.name + " " + newEvent.location + " " + newEvent.date + " " + newEvent.time)
                eventsDao.addEvent(newEvent)
                showEvents()
            }
            .setNegativeButton("Odustani"){ _, _ ->}
            .show()

        dialogHelper.activateDateTimeListeners()
    }

    override fun onResume() {
        super.onResume()
        showEvents()
    }

    private fun showEvents() {
        eventsDao = EventsDao()
        recyclerView = requireView().findViewById(R.id.rv_created_events)
        recyclerView.adapter = EventsAdapter(
            eventsDao
                .getUserEvents(LoggedInUser.user!!)
                .toMutableList()
        )
        recyclerView.layoutManager = LinearLayoutManager(requireView().context)
    }
}