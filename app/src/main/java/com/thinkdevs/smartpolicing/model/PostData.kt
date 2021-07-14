package com.thinkdevs.smartpolicing.model

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class PostData(var name: String? = null, val message: String? = null, var email: String? = null, val phone: String? = null,) {
    // Null default values create a no-argument default constructor, which is needed
    // for deserialization from a DataSnapshot.
}