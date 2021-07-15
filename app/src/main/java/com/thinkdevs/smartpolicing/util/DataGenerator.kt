package com.thinkdevs.smartpolicing.util

import android.content.Context
import com.thinkdevs.smartpolicing.R
import com.thinkdevs.smartpolicing.model.PostData
import java.util.*

class DataGenerator {

    fun getPeopleData(ctx: Context): List<PostData?>? {
        val items: MutableList<PostData?> = ArrayList<PostData?>()
        val drw_arr = ctx.resources.obtainTypedArray(R.array.people_images)
        val name_arr = ctx.resources.getStringArray(R.array.people_names)
        for (i in 0 until drw_arr.length()) {
            val obj = PostData()
//            obj.email = drw_arr.getResourceId(i, -1).toString()
            obj.name = name_arr[i]
            obj.phone =obj.name
            items.add(obj)
        }
        items.shuffle()
        return items
    }
}