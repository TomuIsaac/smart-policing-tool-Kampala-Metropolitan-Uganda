package com.thinkdevs.smartpolicing.util

import android.util.Log
import com.google.firebase.database.*

class DataReader (val onLoaded: Loaded) {

    fun listenOnce(data: DatabaseReference) {
        data.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                Log.println(Log.ASSERT,"Data Error", p0.message)
            }
            override fun onDataChange(p0: DataSnapshot) {
                onLoaded.onLoaded(0, p0)
            }
        })
    }

    fun listen(data: DatabaseReference, pos :Int) {
        data.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                Log.println(Log.ASSERT,"Data Error", p0.message)
            }

            override fun onDataChange(p0: DataSnapshot) {
                onLoaded.onLoaded(pos, p0)
            }
        })
    }
}
