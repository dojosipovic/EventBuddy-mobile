package hr.foi.rampu.project.eventbuddy.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import hr.foi.rampu.project.eventbuddy.R
import hr.foi.rampu.project.eventbuddy.database.EventsDao
import hr.foi.rampu.project.eventbuddy.database.UsersDao
import hr.foi.rampu.project.eventbuddy.entities.Event
import hr.foi.rampu.project.eventbuddy.entities.User
import java.text.SimpleDateFormat
import java.util.Locale
import hr.foi.rampu.project.eventbuddy.helpers.LoggedInUser
import java.util.Date

class EventDetails : AppCompatActivity() {
    val edao = EventsDao()
    val udao = UsersDao()
    lateinit var dobivenEvent: Event
    var alertDialogMain: AlertDialog? = null;
    var eventID = "";
    private val sdfDateTime: SimpleDateFormat = SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.ENGLISH)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event_details)
        val eventName = intent.getStringExtra("eventName")
        eventID = intent.getStringExtra("eventId")!!

        dobivenEvent = edao.getEventById(eventID!!.toInt())!!
        Log.e("EVENT", ""+dobivenEvent.name)
        findViewById<TextView>(R.id.tv_event_details_time_date).text = sdfDateTime.format(dobivenEvent.date)
        findViewById<TextView>(R.id.tv_event_details_title).text = dobivenEvent.name
        findViewById<TextView>(R.id.tv_event_details_location).text = dobivenEvent.location
        findViewById<TextView>(R.id.tv_event_details_overview).text = dobivenEvent.overview

        val buttonClick = findViewById<Button>(R.id.btn_event_details_back)
        buttonClick.setOnClickListener {
            val intent = Intent(this, StartScreen::class.java)
            startActivity(intent)
            finish()
        }

        prikaziSudionike()

        val buttonSubscribe = findViewById<Button>(R.id.btn_event_details_subscribe)
        val buttonParticipants = findViewById<TextView>(R.id.btn_event_details_number_of_participants)

        var isSubbed: Boolean = edao.isUserSubscribedOnEvent(LoggedInUser.user!!.id, dobivenEvent.id)
        var isBanned: Boolean = edao.isUserBanned(LoggedInUser.user!!, dobivenEvent)
        if (isDateInPast(dobivenEvent.date)) buttonSubscribe.visibility = View.GONE
        buttonSubscribe.text = if (isSubbed) "Napusti događaj" else "Pretplati se"
        if (isBanned) buttonSubscribe.isEnabled = false
        buttonSubscribe.setOnClickListener {
            if (isSubbed) {
                edao.unsubscribeUserOnEvent(LoggedInUser.user!!, dobivenEvent)
                buttonSubscribe.text = "Pretplati se"
            } else {
                edao.subscribeUserOnEvent(LoggedInUser.user!!, dobivenEvent)
                buttonSubscribe.text = "Napusti događaj"
            }
            isSubbed = !isSubbed
            buttonParticipants.text = "Sudionici:${edao.getNumberOfParticipants(dobivenEvent.id)}"
        }
    }

    private fun isDateInPast(date: Date): Boolean {
        val currentDate = Date()
        return date.before(currentDate)
    }

    fun showParticipantOptionsDialog(sudionik: User) {
        val options = arrayOf("Ukloni", "Zabrani")

        AlertDialog.Builder(this)
            .setTitle("${sudionik.name} ${sudionik.surname}")
            .setItems(options) { dialog, opcija ->
                if (opcija == 0) {
                    ukloniSudionika(sudionik)
                }else if (opcija == 1) {
                    zabraniSudionika(sudionik)
                }
            }
            .setNegativeButton("Odustani") { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }

    fun prikaziSudionike(){
        dobivenEvent = edao.getEventById(eventID!!.toInt())!!
        findViewById<TextView>(R.id.btn_event_details_number_of_participants).text = "Sudionici:" + dobivenEvent.participants.toString()

        val buttonClick2 = findViewById<Button>(R.id.btn_event_details_number_of_participants)
        buttonClick2.setOnClickListener{
            val korisnici =  edao.getAllParticipantsOnEvent(eventID)
            Log.e("KORISNICI", korisnici.toString())

            val layout = LinearLayout(this)
            layout.orientation = LinearLayout.VERTICAL

            for (k in korisnici) {
                val button = Button(this)
                button.isAllCaps = false
                button.text = k.name + " " + k.surname

                if(!LoggedInUser.user!!.IsOrganizer()) {
                    Toast.makeText(this, "Vi niste organizator", Toast.LENGTH_SHORT).show()
                }else{
                    if(LoggedInUser.user!!.id != dobivenEvent.userId){
                        Toast.makeText(this, "Vi niste organizator ovog događaja", Toast.LENGTH_SHORT).show()
                    }else{
                        button.setOnClickListener {
                            showParticipantOptionsDialog(k)
                        }
                    }
                }
                layout.addView(button)
            }

            val builder = AlertDialog.Builder(this)
            builder.setTitle("Sudionici")
            builder.setView(layout)
            builder.setNegativeButton("Odustani") { dialog, _ ->
                dialog.dismiss()
            }
            alertDialogMain = builder.create()
            alertDialogMain?.show()
        }
    }
    fun ukloniSudionika(sudionik: User) {
        Toast.makeText(this, "Uklonjen ${sudionik.name} ${sudionik.surname}", Toast.LENGTH_SHORT).show()
        udao.kickUser(sudionik.id, eventID.toInt())
        alertDialogMain?.dismiss();
        dobivenEvent = edao.getEventById(eventID!!.toInt())!!
        findViewById<TextView>(R.id.btn_event_details_number_of_participants).text = "Sudionici:" + dobivenEvent.participants.toString()
    }

    fun zabraniSudionika(sudionik: User) {
        Toast.makeText(this, "Zabranjen ${sudionik.name} ${sudionik.surname}", Toast.LENGTH_SHORT).show()
        udao.banUser(sudionik.id, eventID.toInt())
        alertDialogMain?.dismiss();
        dobivenEvent = edao.getEventById(eventID!!.toInt())!!
        findViewById<TextView>(R.id.btn_event_details_number_of_participants).text = "Sudionici:" + dobivenEvent.participants.toString()
    }
}
