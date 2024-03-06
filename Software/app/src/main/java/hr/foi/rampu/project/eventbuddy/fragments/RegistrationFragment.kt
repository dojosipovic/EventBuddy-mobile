package hr.foi.rampu.project.eventbuddy.fragments


import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import hr.foi.rampu.project.eventbuddy.MainActivity
import hr.foi.rampu.project.eventbuddy.R
import hr.foi.rampu.project.eventbuddy.database.UsersDao
import hr.foi.rampu.project.eventbuddy.entities.User


class RegistrationFragment : Fragment() {
    private lateinit var ime: EditText
    private lateinit var prezime: EditText
    private lateinit var korisnickoIme: EditText
    private lateinit var lozinka: EditText
    private lateinit var potvrdiLozinku: EditText
    private lateinit var usersDao: UsersDao

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       val view = inflater.inflate(R.layout.fragment_registration, container, false)

        ime = view.findViewById(R.id.ime)
        prezime = view.findViewById(R.id.prezime)
        korisnickoIme = view.findViewById(R.id.korime)
        lozinka = view.findViewById((R.id.user_lozinka))
        potvrdiLozinku = view.findViewById((R.id.user_lozinka_potvrdi))


        view.findViewById<Button>(R.id.btn_Login_register).setOnClickListener {
            val navRegister = activity as FragmentNavigation
            navRegister.navigateFragment(LoginFragment(), false)
        }
        view.findViewById<Button>(R.id.btn_fragment_login_register).setOnClickListener {
            provjeraUnosa()
        }
        return view
    }

    private fun provjeraUnosa(){

        when{
            TextUtils.isEmpty(ime.text.toString().trim()) ->{
                ime.setError("Unesite ime!")
            }

            TextUtils.isEmpty(prezime.text.toString().trim()) ->{
                prezime.setError("Unesite prezime!")
            }

            TextUtils.isEmpty(korisnickoIme.text.toString().trim()) ->{
                korisnickoIme.setError("Unesite korisničko ime!")
            }

            TextUtils.isEmpty(lozinka.text.toString().trim()) ->{
                lozinka.setError("Unesite lozinku!")
            }

            TextUtils.isEmpty(potvrdiLozinku.text.toString().trim()) ->{
                potvrdiLozinku.setError("Ponovno unesite lozinku! ")
            }

            ime.text.toString().isNotEmpty() && prezime.text.toString().isNotEmpty() &&
                     korisnickoIme.text.toString().isNotEmpty() && lozinka.text.toString().isNotEmpty() &&
                     potvrdiLozinku.text.toString().isNotEmpty() ->
            {
                if(lozinka.text.toString().length >= 8){
                    if(lozinka.text.toString() == potvrdiLozinku.text.toString()){
                        val novi = User(0,ime.text.toString(), prezime.text.toString(), korisnickoIme.text.toString(), lozinka.text.toString(), 0)

                        usersDao = UsersDao()
                        usersDao.addUser(novi)
                        Toast.makeText(context, "Uspješna registracija", Toast.LENGTH_SHORT).show()

                        val intent = Intent(context, MainActivity::class.java)
                        startActivity(intent)
                    }
                    else {
                        potvrdiLozinku.setError("Lozinke se ne podudaraju!")
                    }
                } else{
                    lozinka.setError("Lozinka ima manje od 8 znakova!")
                }
            }
        }
    }

}