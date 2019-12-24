package com.anum.locdagger.base

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

abstract class BaseAdapter<VH : BaseViewHolder, T : BaseEntity>(list: List<T>?)
    : RecyclerView.Adapter<VH>() {

    protected var entityList : MutableList<T>? = null

    init {
        entityList = list?.toMutableList()
    }

    override fun getItemCount(): Int {
        var size = 0
        entityList?.let { size = it.size }
        return size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val view = LayoutInflater.from(parent.context).inflate(itemLayoutResource, null)
        return getViewHolder(view, viewType)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        entityList?.get(position)?.let { holder.onBind(it) }
    }

    protected abstract val itemLayoutResource: Int

    protected abstract fun getViewHolder(itemView: View, viewType: Int): VH

    open fun addItems(aList : List<T>) {
        entityList?.addAll(aList)
        notifyDataSetChanged()
    }
}

