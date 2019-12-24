package com.anum.locdagger.base

import android.view.View
import androidx.recyclerview.widget.RecyclerView

abstract class BaseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    init {
        initUI()
        setActionOnItemView()
    }

    protected abstract fun initUI()

    abstract fun onBind(entity: BaseEntity)

    protected abstract fun onClick()

    protected fun setActionOnItemView() {
        itemView.setOnClickListener { this@BaseViewHolder.onClick() }
    }
}
