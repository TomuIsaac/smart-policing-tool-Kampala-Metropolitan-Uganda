package com.thinkdevs.smartpolicing.util

import android.content.Context
import android.content.SharedPreferences

class Sharedpref (context:Context){
    val pref : SharedPreferences = context.getSharedPreferences("umu_db", 0)

    var islogin:Boolean
    get() = pref.getBoolean("islogin", false)
    set(value) = pref.edit().putBoolean("islogin", value).apply()

    var userType:String?
    get() = pref.getString("userType","")
    set(value) = pref.edit().putString("userType", value).apply()

    var userName:String?
        get() = pref.getString("userName","")
        set(value) = pref.edit().putString("userName", value).apply()

    var userPhone:String?
        get() = pref.getString("userPhone","")
        set(value) = pref.edit().putString("userPhone", value).apply()
}