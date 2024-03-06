package hr.foi.rampu.project.eventbuddy.fragments

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import hr.foi.rampu.project.eventbuddy.R
import hr.foi.rampu.project.eventbuddy.database.UsersDao
import hr.foi.rampu.project.eventbuddy.helpers.LoggedInUser

class EditFragment : Fragment() {
    private lateinit var organizerDialogView: View
    var udao = UsersDao()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_edit, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        popuniInputeSaStarimPodacima()

        val btnOdustani = view.findViewById<MaterialButton>(R.id.btn_edit_odustani)
        btnOdustani.setOnClickListener {
            popuniInputeSaStarimPodacima()
        }

        val btnSpremi = view.findViewById<MaterialButton>(R.id.btn_edit_spremi)
        btnSpremi.setOnClickListener {
            val ime = view.findViewById<TextInputEditText>(R.id.ime_uredi_profil).text.toString()
            val prezime = view.findViewById<TextInputEditText>(R.id.prezime_uredi_profil).text.toString()
            val staraLozinka = view.findViewById<TextInputEditText>(R.id.user_lozinka_uredi_profil).text.toString()
            val novaLozinka = view.findViewById<TextInputEditText>(R.id.user_lozinka_potvrdi_uredi_profil).text.toString()
            //val postojeciUser = MockDataLoader.logedInUser
            val postojeciUser = LoggedInUser.user
            if(postojeciUser!!.password == staraLozinka){
                if(novaLozinka == ""){
                    udao.updateUser(postojeciUser, ime, prezime, staraLozinka)
                    //MockDataLoader.spremiNovePodatke(User(postojeciUser.id, ime, prezime, korisnickoIme, staraLozinka, postojeciUser.warnings))
                    Toast.makeText(context, "Uspješno spremljeni podaci!", Toast.LENGTH_SHORT).show()
                    popuniInputeSaStarimPodacima()
                }else {
                    if(novaLozinka.length >= 8){
                        udao.updateUser(postojeciUser, ime, prezime, novaLozinka)
                        //MockDataLoader.spremiNovePodatke(User(postojeciUser.id, ime, prezime, korisnickoIme, novaLozinka, postojeciUser.warnings))
                        Toast.makeText(context, "Uspješno spremljeni podaci!", Toast.LENGTH_SHORT).show()
                        Toast.makeText(context, "Spremljena nova lozinka!", Toast.LENGTH_SHORT).show()
                        popuniInputeSaStarimPodacima()
                    }else{
                        Toast.makeText(context, "Nova lozinka ima manje od 8 znakova!", Toast.LENGTH_SHORT).show()
                    }
                }
            }else{
                Toast.makeText(context, "Unesite točnu staru lozinku!", Toast.LENGTH_SHORT).show()
            }
        }

        val organizer = LoggedInUser.user!!.IsOrganizer()
        val btnOrganizer = requireView().findViewById<MaterialButton>(R.id.btn_Organizer)
        btnOrganizer.visibility = if (organizer) View.GONE else View.VISIBLE

        if (UsersDao().hasUserOrganizerRequest(LoggedInUser.user!!)) {
            btnOrganizer.isEnabled = false
            btnOrganizer.text = "Zahtjev u obradi"
        }


        organizerDialogView = LayoutInflater
            .from(requireContext())
            .inflate(R.layout.organizer_dialog, null)

        btnOrganizer.setOnClickListener{
            val parentView = organizerDialogView.parent as? ViewGroup
            parentView?.removeView(organizerDialogView)
            val reasonEditText = organizerDialogView.findViewById<EditText>(R.id.et_organizer_dialog_reason)
            reasonEditText.text.clear()

            AlertDialog.Builder(context)
                .setView(organizerDialogView)
                .setTitle("Postani organizator")
                .setPositiveButton("Pošalji") { _, _ ->
                    val reason = reasonEditText.text.toString().trim()
                    if (reason.length < 20) {
                        Toast.makeText(view.context,"Upišite minimalno 20 znakova", Toast.LENGTH_SHORT).show()
                    } else {
                        val usersDao = UsersDao()
                        usersDao.sendOrganizerRequest(LoggedInUser.user!!, reason)
                        Toast.makeText(view.context,"Zahtjev uspješno poslan", Toast.LENGTH_SHORT).show()
                        btnOrganizer.isEnabled = false
                        btnOrganizer.text = "Zahtjev u obradi"
                    }
                }
                .setNegativeButton("Odustani"){ dialog, _ ->
                    dialog.dismiss()
                }
                .show()
        }
    }

    private fun popuniInputeSaStarimPodacima(){

        val prijavljenUser = udao.getUserByUsername(LoggedInUser.user!!.username)
        LoggedInUser.user = prijavljenUser!!
        view?.findViewById<TextInputEditText>(R.id.ime_uredi_profil)?.setText(prijavljenUser.name)
        view?.findViewById<TextInputEditText>(R.id.prezime_uredi_profil)?.setText(prijavljenUser.surname)
        view?.findViewById<TextInputEditText>(R.id.korime_uredi_profil)?.setText(prijavljenUser.username)
        view?.findViewById<TextInputEditText>(R.id.user_lozinka_uredi_profil)?.setText("")
        view?.findViewById<TextInputEditText>(R.id.user_lozinka_potvrdi_uredi_profil)?.setText("")
    }
}