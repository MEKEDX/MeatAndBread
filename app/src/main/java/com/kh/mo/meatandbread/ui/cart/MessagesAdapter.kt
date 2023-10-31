package com.kh.mo.meatandbread.ui.cart

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.kh.mo.meatandbread.R
import com.kh.mo.meatandbread.model.WayOfSend

class MessagesAdapter (
    private val context: Context,
    private val items: Array<WayOfSend>,
    private val click:(Int)->Unit
) : RecyclerView.Adapter<MessagesAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.message_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val message = items[position]
        Glide.with(context).load(message.image).error(
            R.drawable.ic_launcher_background
        ).into(holder.iconImageView)
        holder.textTextView.text = message.sendBy
        holder.itemView.setOnClickListener{
            click(position)
        }
    }

    override fun getItemCount()= items.size


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val iconImageView: ImageView = itemView.findViewById(R.id.message_icon)
        val textTextView: TextView = itemView.findViewById(R.id.message_title)
    }
}