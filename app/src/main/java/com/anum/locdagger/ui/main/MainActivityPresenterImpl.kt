package com.anum.locdagger.ui.main

import java.lang.ref.WeakReference

class MainActivityPresenterImpl : MainActivityContract.Presenter {

    private var view : WeakReference<MainActivityContract.View>? = null

    override fun attachView(view: MainActivityContract.View) {
        this.view = WeakReference(view)
    }

    override fun detachView() {
        this.view = null
    }

}