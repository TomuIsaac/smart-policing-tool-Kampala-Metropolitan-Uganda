package com.thinkdevs.smartpolicing.util

import android.content.Context
import android.graphics.Bitmap
import android.widget.ImageView
import androidx.annotation.DrawableRes
import androidx.core.graphics.drawable.RoundedBitmapDrawable
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.BitmapImageViewTarget
import com.thinkdevs.smartpolicing.R
import com.thinkdevs.smartpolicing.model.PostData
import java.util.*

fun displayImageRound(ctx: Context, img: ImageView, @DrawableRes drawable: String?) {
    try {
        Glide.with(ctx).load(drawable).asBitmap().centerCrop()
            .into(object : BitmapImageViewTarget(img) {
                override fun setResource(resource: Bitmap?) {
                    val circularBitmapDrawable: RoundedBitmapDrawable =
                        RoundedBitmapDrawableFactory.create(ctx.resources, resource)
                    circularBitmapDrawable.isCircular = true
                    img.setImageDrawable(circularBitmapDrawable)
                }
            })
    } catch (e: Exception) {
    }
}

fun getPeopleData(ctx: Context): MutableList<PostData> {
    val items: MutableList<PostData> = ArrayList<PostData>()
    val drw_arr = ctx.resources.obtainTypedArray(R.array.people_images)
    val name_arr = ctx.resources.getStringArray(R.array.people_names)
    for (i in 0 until drw_arr.length()) {
        val obj = PostData()
//        obj.email = drw_arr.getResourceId(i, -1).toString()
        obj.name = name_arr[i]
        obj.phone =obj.name
//        obj.imageDrw = ctx.resources.getDrawable(obj.image)

        items.add(obj)
    }
    items.shuffle()
    return items
}
