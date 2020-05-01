package com.manuelrojas.navigation

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity

object ProvideIntent {

    fun forActivity(context: Context, activityName: String): Intent? {
        return Intent(context, loadClass<AppCompatActivity>(activityName))
    }
}