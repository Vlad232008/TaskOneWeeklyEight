package com.example.taskoneweeklyeight.manager

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.taskoneweeklyeight.R

object FragmentManager {
    var currentFrag: Fragment? = null

    fun setFragment(newFrag: Fragment, activity: AppCompatActivity) {
        val transaction = activity.supportFragmentManager.beginTransaction()
        transaction.replace(R.id.placeHolder, newFrag)
        transaction.commit()
        currentFrag = newFrag
    }
}