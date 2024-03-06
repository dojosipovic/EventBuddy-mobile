package hr.foi.rampu.project.eventbuddy.fragments

import androidx.fragment.app.Fragment

interface FragmentNavigation {
    fun navigateFragment(fragment: Fragment, addToStack: Boolean)
}