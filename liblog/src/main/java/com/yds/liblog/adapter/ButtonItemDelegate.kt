package com.yds.liblog.adapter

import android.view.View
import android.view.View.OnClickListener
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import com.yds.liblog.R

class ButtonItemDelegate(
    val buttonTitle: String,
    val onClickListener: OnClickListener
) : ItemDelegate() {


    class ButtonViewHolder(private val root: View) : RecyclerView.ViewHolder(root) {
        val btn = root.findViewById<Button>(R.id.btn)
    }

    override fun createViewHolder(view: View): ButtonViewHolder {
        return ButtonViewHolder(view)
    }

    override fun getLayoutId(): Int {
        return R.layout.liblog_item_button
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val viewHolder = holder as? ButtonViewHolder
        viewHolder?.btn?.text = buttonTitle
        viewHolder?.btn?.setOnClickListener {
            onClickListener.onClick(it)
        }
    }


}