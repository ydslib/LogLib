package com.yds.liblog.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

abstract class ItemDelegate {

    val itemViewType: Int
        get() = this.javaClass.simpleName.hashCode()


    fun createViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(getLayoutId(), parent, false)
        return createViewHolder(view)
    }

    abstract fun getLayoutId(): Int

    abstract fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int)

    abstract fun createViewHolder(view: View): RecyclerView.ViewHolder

}