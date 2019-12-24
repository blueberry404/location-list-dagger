package com.anum.locdagger.ui.main

import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import com.anum.locdagger.R
import com.anum.locdagger.base.BaseAppActivity
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject


class MainActivity : BaseAppActivity<MainActivityContract.View, MainActivityContract.Presenter>(),
MainActivityContract.View, HasAndroidInjector {

    @Inject lateinit var fragmentDispatchingAndroidInjector: DispatchingAndroidInjector<Any>

    @Inject lateinit var presenter : MainActivityContract.Presenter
    @Inject lateinit var appBarConfiguration : AppBarConfiguration

    override fun instantiatePresenter(): MainActivityContract.Presenter = presenter

    override fun layoutResource(): Int = R.layout.activity_main

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)

        val navController = findNavController(R.id.nav_host_fragment)
        setupActionBarWithNavController(navController, appBarConfiguration)
    }

    override fun androidInjector(): AndroidInjector<Any> = fragmentDispatchingAndroidInjector
}
