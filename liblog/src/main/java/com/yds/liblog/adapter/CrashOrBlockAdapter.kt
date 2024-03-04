package com.yds.liblog.adapter

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.yds.liblog.R
import com.yds.liblog.db.CrashModel

class CrashOrBlockAdapter(
    val dataList: MutableList<CrashModel>
) : Adapter<CrashOrBlockAdapter.LogViewHolder>() {


    class LogViewHolder(val root: View) : ViewHolder(root) {
        val time = root.findViewById<TextView>(R.id.liblog_time)
        val msg = root.findViewById<TextView>(R.id.liblog_msg)
        val detail = root.findViewById<TextView>(R.id.liblog_detail)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LogViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_crash_or_block, parent, false)
        return LogViewHolder(view)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun onBindViewHolder(holder: LogViewHolder, position: Int) {
        val item = dataList[position]
        holder.let {
            val h = item.time?.split(" ")
            if (h?.size == 2) {
                it.time.text = h[1]
            } else {
                it.time.text = item.time
            }
            it.msg.text = item.msg
            it.detail.text = item.toString()
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