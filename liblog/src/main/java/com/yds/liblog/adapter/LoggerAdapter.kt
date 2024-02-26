package com.yds.liblog.adapter

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.yds.liblog.R
import com.yds.liblog.db.LoggerModel
import com.yds.liblog.factory.Logger

class LoggerAdapter(
    val dataList: MutableList<LoggerModel>
) : Adapter<LoggerAdapter.LoggerViewHolder>() {


    class LoggerViewHolder(val root: View) : ViewHolder(root) {
        val time = root.findViewById<TextView>(R.id.time)
        val tag = root.findViewById<TextView>(R.id.tag)
        val level = root.findViewById<TextView>(R.id.level)
        val summary = root.findViewById<TextView>(R.id.summary)
        val detail = root.findViewById<TextView>(R.id.detail)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LoggerViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_logger, parent, false)
        return LoggerViewHolder(view)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onBindViewHolder(holder: LoggerViewHolder, position: Int) {
        val model = dataList[position]
        holder.level.text = model.level
        holder.tag.text = model.tag
        holder.summary.text = model.msg.toString()
        val h = model.time?.split(" ")
        if (h?.size == 2) {
            holder.time.text = h[1]
        } else {
            holder.time.text = model.time
        }

        holder.detail.text = model.toString()
        holder.level.setTextColor(Color.parseColor("#000000"))
        when (model.level) {
            Logger.I -> {
                holder.level.setBackgroundColor(Color.parseColor("#E9F5E6"))
            }

            Logger.D -> {
                holder.level.setBackgroundColor(Color.parseColor("#C9EDFF"))
            }

            Logger.E -> {
                holder.level.setTextColor(Color.parseColor("#FFFFFF"))
                holder.level.setBackgroundColor(Color.parseColor("#FF3300"))
            }

            Logger.W -> {
                holder.level.setBackgroundColor(Color.parseColor("#F5EA9D"))
            }

            Logger.V -> {
                holder.level.setBackgroundColor(Color.parseColor("#D6D6D6"))
            }
        }
        holder.let {
            it.detail.setOnLongClickListener { v ->
                val cm = v.context.getSystemService(Context.CLIPBOARD_SERVICE) as? ClipboardManager
                val clipData = ClipData.newPlainText("Label", "${it.detail.text?.toString()}")
                cm?.setPrimaryClip(clipData)
                Toast.makeText(v.context, "Copy Success", Toast.LENGTH_SHORT).show()
                false
            }
            it.root.setOnClickListener { v ->
                it.detail.isVisible = !it.detail.isVisible
            }
        }
    }

}