package com.anum.locdagger.di

import androidx.navigation.ui.AppBarConfiguration
import com.anum.locdagger.R
import com.anum.locdagger.di.scope.ActivityScope
import com.anum.locdagger.ui.main.MainActivityContract
import com.anum.locdagger.ui.main.MainActivityPresenterImpl
import dagger.Module
import dagger.Provides

@Module
class ActivityModule {

    @ActivityScope
    @Provides fun getPresenter() : MainActivityContract.Presenter =
        MainActivityPresenterImpl()

    @ActivityScope
    @Provides fun getAppBarConfiguration() : AppBarConfiguration =
        AppBarConfiguration(
            setOf(
                R.id.navigation_vehicle,
                R.id.navigation_map
            )
        )
}