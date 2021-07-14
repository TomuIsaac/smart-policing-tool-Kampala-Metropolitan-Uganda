package com.thinkdevs.smartpolicing.util

import com.google.firebase.database.DataSnapshot

interface Loaded {
    fun onLoaded(x: Int, data: DataSnapshot)
}