package com.thinkdevs.smartpolicing.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.thinkdevs.smartpolicing.R
import com.thinkdevs.smartpolicing.model.PostData
import com.thinkdevs.smartpolicing.util.displayImageRound
import java.util.*

class AdapterListBasic(context: Context, items: List<PostData>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var items: List<PostData> = ArrayList()
    private val ctx: Context
    private var mOnItemClickListener: OnItemClickListener? = null

    interface OnItemClickListener {
        fun onItemClick(view: View?, obj: PostData?, position: Int)
    }

    fun setOnItemClickListener(mItemClickListener: OnItemClickListener?) {
        mOnItemClickListener = mItemClickListener
    }

    inner class OriginalViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        var image: ImageView = v.findViewById<View>(R.id.image) as ImageView
        var name: TextView
        var lyt_parent: View

        init {
            name = v.findViewById<View>(R.id.name) as TextView
            lyt_parent = v.findViewById(R.id.lyt_parent) as View
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val vh: RecyclerView.ViewHolder
        val v = LayoutInflater.from(parent.context).inflate(R.layout.report_item, parent, false)
        vh = OriginalViewHolder(v)
        return vh
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is OriginalViewHolder) {
            val view = holder
            val (name, _, email) = items[position]
            view.name.text = name
            displayImageRound(ctx, view.image, email)
            view.lyt_parent.setOnClickListener { view ->
                if (mOnItemClickListener != null) {
                    mOnItemClickListener!!.onItemClick(view, items[position], position)
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    init {
        this.items = items
        ctx = context
    }
}