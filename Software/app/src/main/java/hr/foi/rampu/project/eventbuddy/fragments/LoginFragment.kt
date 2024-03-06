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
import hr.foi.rampu.project.eventbuddy.R
import hr.foi.rampu.project.eventbuddy.activities.StartScreen
import hr.foi.rampu.project.eventbuddy.database.UsersDao
import hr.foi.rampu.project.eventbuddy.helpers.LoggedInUser
import hr.foi.rampu.project.eventbuddy.helpers.MockDataLoader


class LoginFragment : Fragment() {
    private lateinit var korisnik_prijava: EditText
    private lateinit var korisnik_lozinka: EditText
    private lateinit var usersDao: UsersDao

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_login, container, false)

        korisnik_prijava = view.findViewById(R.id.korisnik_prijava)
        korisnik_lozinka = view.findViewById(R.id.korisnik_lozinka)

        view.findViewById<Button>(R.id.btn_fragment_login_register).setOnClickListener {
            val navRegister = activity as FragmentNavigation
            navRegister.navigateFragment(RegistrationFragment(), false)
        }

        view.findViewById<Button>(R.id.btn_Login).setOnClickListener {
            provjeraUnosaLogin()
        }

        return view
    }
    private fun provjeraUnosaLogin(){

        usersDao = UsersDao()
        when {
            TextUtils.isEmpty(korisnik_prijava.text.toString().trim()) -> {
                korisnik_prijava.setError("Unesite korisničko ime!")
            }
            TextUtils.isEmpty(korisnik_lozinka.text.toString().trim()) -> {
                korisnik_lozinka.setError("Ponovno unesite lozinku!")
            }

            else -> {
                val user = usersDao.getUserByUsername(korisnik_prijava.text.toString().trim())
                if(user == null) {
                    korisnik_prijava.setError("Unesite točno korisničko ime!")
                }
                else if (user.password == korisnik_lozinka.text.toString().trim()) {
                    Toast.makeText(context, "Uspješna prijava", Toast.LENGTH_SHORT).show()
                    MockDataLoader.logedInUser = user
                    LoggedInUser.user = user
                    val intent = Intent(context, StartScreen::class.java)
                    startActivity(intent)
                }
                else {
                    korisnik_lozinka.setError("Unesite točnu lozinku!")
                }
            }
        }

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val buttonClick = view.findViewById<Button>(R.id.btn_fragment_login_register)
        buttonClick.setOnClickListener {
            val registrationFragment = RegistrationFragment()
            activity?.supportFragmentManager?.beginTransaction()
                ?.replace(R.id.container, registrationFragment)
                ?.commit()
        }
    }
}