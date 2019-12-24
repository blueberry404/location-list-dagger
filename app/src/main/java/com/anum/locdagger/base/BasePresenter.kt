package com.anum.locdagger.base

interface BasePresenter<in V : BaseView> {

    fun attachView(view: V)

    fun detachView()
}