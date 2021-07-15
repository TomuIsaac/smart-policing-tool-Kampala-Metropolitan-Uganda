package com.thinkdevs.smartpolicing.model

import com.google.firebase.database.IgnoreExtraProperties

data class PostData(var phone: String? = null,var name: String? = null, val message: String? = null) {

}