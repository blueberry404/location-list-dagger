package com.anum.locdagger.di

import com.anum.locdagger.di.scope.ActivityScope
import com.anum.locdagger.ui.main.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBindingModule {

    @ActivityScope
    @ContributesAndroidInjector(modules =
    [
        ActivityModule::class,
        FragmentBindingModule::class
    ])
    internal abstract fun mainActivity() : MainActivity
}

