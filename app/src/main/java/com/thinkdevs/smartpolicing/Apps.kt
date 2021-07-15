package com.thinkdevs.smartpolicing

import android.app.Application
import com.google.firebase.FirebaseApp
import com.thinkdevs.smartpolicing.util.Sharedpref

class Apps :Application(){
    override fun onCreate() {
        pref = Sharedpref(applicationContext)
        super.onCreate()
        FirebaseApp.initializeApp(this)
    }
    companion object{
        lateinit var pref:Sharedpref
        init {

        }
    }

}