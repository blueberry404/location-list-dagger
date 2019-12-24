package com.anum.locdagger.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

abstract class BaseFragment<in V : BaseView, T : BasePresenter<V>> :
    Fragment(), BaseView {

    private lateinit var mPresenter : T

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mPresenter = instantiatePresenter()
        return inflater.inflate(layoutResource(), container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
    }

    override fun onResume() {
        super.onResume()
        mPresenter?.attachView(this as V)
    }

    override fun onPause() {
        super.onPause()
        mPresenter?.detachView()
    }

    override fun getToolbarId(): Int = 0

    protected abstract fun instantiatePresenter() : T

    protected open fun initUI() {
        //left for optional override
    }
}