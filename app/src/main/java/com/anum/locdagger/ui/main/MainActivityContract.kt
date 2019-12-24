package com.anum.locdagger.ui.main

import com.anum.locdagger.base.BasePresenter
import com.anum.locdagger.base.BaseView

interface MainActivityContract {

    interface View: BaseView

    interface Presenter: BasePresenter<View>
}