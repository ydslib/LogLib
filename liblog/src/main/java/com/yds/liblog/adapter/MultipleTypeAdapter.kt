package com.yds.liblog.adapter

import android.util.SparseArray
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class MultipleTypeAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val dataList = arrayListOf<ItemDelegate>()

    private val mViewTypes: SparseArray<ItemDelegate> by lazy {
        SparseArray<ItemDelegate>()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val item = mViewTypes[viewType] ?: throw ExceptionInInitializerError("please init this viewType from item")
        return item.createViewHolder(parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        dataList[position].onBindViewHolder(holder, position)
    }

    override fun getItemViewType(position: Int): Int {
        return dataList[position].itemViewType
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    fun addItem(item: ItemDelegate) {
        dataList.add(item)
        val viewType = item.itemViewType
        if (mViewTypes[viewType] == null) {
            mViewTypes.put(viewType, item)
        }
    }

    fun clear() {
        dataList.clear()
        mViewTypes.clear()
    }
}