package hr.foi.rampu.project.eventbuddy.adapters

import android.app.AlertDialog
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import hr.foi.rampu.project.eventbuddy.R
import hr.foi.rampu.project.eventbuddy.activities.EventDetails
import hr.foi.rampu.project.eventbuddy.database.EventsDao
import hr.foi.rampu.project.eventbuddy.entities.Event
import hr.foi.rampu.project.eventbuddy.helpers.EditEventDialogHelper
import hr.foi.rampu.project.eventbuddy.helpers.LoggedInUser
import java.text.SimpleDateFormat
import java.util.Locale

class EventsAdapter(private val eventsList: MutableList<Event>) :
    RecyclerView.Adapter<EventsAdapter.EventViewHolder>() {
    private var eventsDao: EventsDao = EventsDao()

    inner class EventViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val sdfDate: SimpleDateFormat = SimpleDateFormat("dd.MM.yyyy", Locale.ENGLISH)
        private val sdfTime: SimpleDateFormat = SimpleDateFormat("HH:mm", Locale.ENGLISH)

        private val eventName: TextView
        private val eventDate: TextView
        private val eventTime: TextView
        private val eventLocation: TextView
        private val eventParticipants: TextView
        private val eventId: TextView

        init {
            eventName = view.findViewById(R.id.tv_event_name)
            eventDate = view.findViewById(R.id.tv_event_date)
            eventTime = view.findViewById(R.id.tv_event_time)
            eventLocation = view.findViewById(R.id.tv_event_location)
            eventParticipants = view.findViewById(R.id.tv_event_participants)
            eventId = view.findViewById(R.id.tv_event_id)

            view.setOnClickListener {
                val intent = Intent(view.context, EventDetails::class.java)
                intent.putExtra("eventId", ""+eventId.text.toString())
                intent.putExtra("eventName", ""+eventName.text.toString())
                view.context.startActivity(intent)
            }

            view.setOnLongClickListener {
                val eventId = this.eventId.text.toString().toInt()
                val dobivenEvent = eventsDao.getEventById(eventId)
                if(LoggedInUser.user!!.IsOrganizer()){
                    if(LoggedInUser.user!!.id == dobivenEvent!!.userId){
                        val editTaskDialogView = LayoutInflater
                            .from(view.context)
                            .inflate(R.layout.edit_event_dialog, null)

                        val dialogHelper = EditEventDialogHelper(editTaskDialogView)

                        Log.e("DOBIVEN_EVENT", dobivenEvent!!.name)

                        editTaskDialogView.findViewById<TextView>(R.id.et_edit_event_dialog_eventName).text = dobivenEvent.name
                        editTaskDialogView.findViewById<TextView>(R.id.et_edit_event_dialog_date).text = sdfDate.format(dobivenEvent.date)
                        editTaskDialogView.findViewById<TextView>(R.id.et_edit_event_dialog_time).text = sdfTime.format(dobivenEvent.time)
                        editTaskDialogView.findViewById<TextView>(R.id.et_edit_event_dialog_location).text = dobivenEvent.location
                        editTaskDialogView.findViewById<TextView>(R.id.et_edit_event_dialog_overview).text = dobivenEvent.overview

                        val cancelEventButton = editTaskDialogView.findViewById<Button>(R.id.btn_edit_event_dialog_cancelEvent)

                        AlertDialog.Builder(view.context)
                            .setView(editTaskDialogView)
                            .setTitle("Uredi događaj")
                            .setPositiveButton("Uredi") { _, _ ->
                                val updatedEvent = dialogHelper.buildEvent(eventId)
                                if(cancelEventButton.text == "Otkaži događaj"){
                                    eventsDao.updateEvent(updatedEvent)
                                }else{
                                    eventsDao.cancelEvent(updatedEvent)
                                }
                            }
                            .setNegativeButton("Odustani"){ _, _ ->}
                            .show()

                        dialogHelper.activateDateTimeListeners()

                        cancelEventButton.setOnClickListener{
                            if(cancelEventButton.text != "Klikom na uredi, potvrđujete otkazivanje događaja!"){
                                cancelEventButton.text = "Klikom na uredi, potvrđujete otkazivanje događaja!"
                            }else{
                                cancelEventButton.text = "Otkaži događaj"
                            }
                        }

                    }else{
                        Toast.makeText(view.context, "Vi niste organizator ovog događaja!", Toast.LENGTH_SHORT).show()
                    }
                }else{
                    Toast.makeText(view.context, "Vi niste organizator!", Toast.LENGTH_SHORT).show()
                }
                true
            }
        }

        fun bind(event: Event) {
            eventName.text = event.name
            eventDate.text = sdfDate.format(event.date)
            eventTime.text = sdfTime.format(event.time)
            eventLocation.text = event.location
            eventParticipants.text = event.participants.toString()
            eventId.text = event.id.toString()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        val eventView = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.event_list_item, parent, false)
        return EventViewHolder(eventView)
    }

    override fun getItemCount(): Int {
        return eventsList.size
    }

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        holder.bind(eventsList[position])
    }
}