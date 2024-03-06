package hr.foi.rampu.project.eventbuddy.fragments

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context.NOTIFICATION_SERVICE
import android.content.Intent
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
import hr.foi.rampu.project.eventbuddy.services.NotificationService

class HomepageEventsFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var eventsDao: EventsDao

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_homepage_events, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val channel = NotificationChannel ("prihvacen-channel", "Prihvacen channel",
            NotificationManager.IMPORTANCE_HIGH)
        val notificationManager = requireContext().getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        recyclerView = view.findViewById(R.id.rv_homepage_events)
        eventsDao = EventsDao()
        recyclerView.adapter = EventsAdapter(eventsDao.getAllEvents().toMutableList())
        recyclerView.layoutManager = LinearLayoutManager(view.context)


        val odgovor = LoggedInUser.user!!.checkForRequest()
        if(odgovor){
            LoggedInUser.user!!.deleteRequest()
            val intent = Intent(view.context, NotificationService::class.java).apply {
                putExtra("prihvacen", odgovor)
            }
            view.context.startForegroundService(intent)
        }
        if(LoggedInUser.user!!.warnings > 0){
            val intent = Intent(view.context, NotificationService::class.java).apply {
                putExtra("upozorenja", LoggedInUser.user!!.warnings)
            }
            view.context.startForegroundService(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        recyclerView = requireView().findViewById(R.id.rv_homepage_events)
        eventsDao = EventsDao()
        recyclerView.adapter = EventsAdapter(eventsDao.getAllEvents().toMutableList())
        recyclerView.layoutManager = LinearLayoutManager(requireView().context)
    }
}