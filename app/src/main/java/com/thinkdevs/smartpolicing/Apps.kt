package com.thinkdevs.smartpolicing

import android.app.Application
import com.google.firebase.FirebaseApp

class Apps :Application(){

    override fun onCreate() {
        super.onCreate()
        FirebaseApp.initializeApp(this)
    }

}