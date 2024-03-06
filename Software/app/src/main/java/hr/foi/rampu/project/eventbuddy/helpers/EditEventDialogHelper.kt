package hr.foi.rampu.project.eventbuddy.helpers

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.view.View
import android.widget.EditText
import android.widget.TextView
import hr.foi.rampu.project.eventbuddy.R
import hr.foi.rampu.project.eventbuddy.entities.Event
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class EditEventDialogHelper(private val view: View) {
    private val selectedDateTime: Calendar = Calendar.getInstance()

    private val sdfDate: SimpleDateFormat = SimpleDateFormat("dd.MM.yyyy", Locale.ENGLISH)
    private val sdfTime: SimpleDateFormat = SimpleDateFormat("HH:mm", Locale.ENGLISH)

    private val dateSelection = view.findViewById<EditText>(R.id.et_edit_event_dialog_date)
    private val timeSelection = view.findViewById<EditText>(R.id.et_edit_event_dialog_time)

    fun activateDateTimeListeners() {
        dateSelection.setOnFocusChangeListener { view, hasFocus ->
            if (hasFocus) {
                DatePickerDialog(
                    view.context,
                    { _, year, monthOfYear, dayOfMonth ->
                        selectedDateTime.set(year, monthOfYear, dayOfMonth)
                        dateSelection.setText(sdfDate.format(selectedDateTime.time).toString())
                    },
                    selectedDateTime.get(Calendar.YEAR),
                    selectedDateTime.get(Calendar.MONTH),
                    selectedDateTime.get(Calendar.DAY_OF_MONTH),
                ).show()
                view.clearFocus()
            }
        }

        timeSelection.setOnFocusChangeListener { view, hasFocus ->
            if (hasFocus) {
                TimePickerDialog(
                    view.context,
                    { _, hourOfDay, minute ->
                        selectedDateTime.set(Calendar.HOUR_OF_DAY, hourOfDay)
                        selectedDateTime.set(Calendar.MINUTE, minute)
                        timeSelection.setText(sdfTime.format(selectedDateTime.time).toString())
                    },
                    selectedDateTime.get(Calendar.HOUR_OF_DAY),
                    selectedDateTime.get(Calendar.MINUTE),
                    true
                ).show()
                view.clearFocus()
            }
        }
    }

    fun buildEvent(id: Int): Event {
        val eventName = view.findViewById<TextView>(R.id.et_edit_event_dialog_eventName).text.toString()
        val location = view.findViewById<TextView>(R.id.et_edit_event_dialog_location).text.toString()
        val overview = view.findViewById<TextView>(R.id.et_edit_event_dialog_overview).text.toString()

        if(sdfDate.format(selectedDateTime.time) == view.findViewById<TextView>(R.id.et_edit_event_dialog_date).text.toString()){
            return Event(id, eventName, overview, selectedDateTime.time, selectedDateTime.time, location, 1, 1, 1)
        }else{
            val date = view.findViewById<TextView>(R.id.et_edit_event_dialog_date).text.toString()
            val time = view.findViewById<TextView>(R.id.et_edit_event_dialog_time).text.toString()
            val dateParsiran = SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.ENGLISH).parse(date + " " + time)
            return Event(id, eventName, overview, dateParsiran, dateParsiran, location, 1, 1, 1)
        }
    }
}