package hr.foi.rampu.project.eventbuddy

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.content.Intent
import androidx.fragment.app.Fragment
import hr.foi.rampu.project.eventbuddy.activities.StartScreen
import hr.foi.rampu.project.eventbuddy.fragments.FragmentNavigation
import hr.foi.rampu.project.eventbuddy.fragments.LoginFragment

class MainActivity : AppCompatActivity(), FragmentNavigation {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager.beginTransaction()
            .add(R.id.container,LoginFragment())
            .commit()

        val buttonClick = findViewById<Button>(R.id.button)
        buttonClick.setOnClickListener {
            val intent = Intent(this, StartScreen::class.java)
            startActivity(intent)
        }
    }

    override fun navigateFragment(fragment: Fragment, addToStack: Boolean) {
        val transaction = supportFragmentManager
            .beginTransaction()
            .replace(R.id.container, fragment)

        if (addToStack){
            transaction.addToBackStack(null)
        }
        transaction.commit()
    }
}