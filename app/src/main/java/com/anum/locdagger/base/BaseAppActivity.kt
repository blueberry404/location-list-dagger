package com.anum.locdagger.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar

abstract class BaseAppActivity<in V : BaseView, T : BasePresenter<V>> :
    AppCompatActivity(), BaseView {

    private lateinit var mPresenter : T
    protected var toolbar : Toolbar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layoutResource())
        mPresenter = instantiatePresenter()
//        setupToolbar()
    }

    override fun onResume() {
        super.onResume()
        mPresenter?.attachView(this as V)
    }

    override fun onPause() {
        super.onPause()
        mPresenter?.detachView()
    }

    fun setupToolbar() {
        if(getToolbarId() == 0) return
        toolbar = findViewById(getToolbarId())
        setSupportActionBar(toolbar)
    }

    override fun getToolbarId(): Int = 0

    protected abstract fun instantiatePresenter() : T
}