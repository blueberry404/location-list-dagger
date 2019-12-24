package com.anum.locdagger.di.app

import com.anum.locdagger.LocDaggerApplication
import com.anum.locdagger.di.ActivityBindingModule
import com.anum.locdagger.service.NetworkModule
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = [
    AndroidSupportInjectionModule::class,
    ActivityBindingModule::class,
    NetworkModule::class
])
interface AppComponent {

    fun inject(application: LocDaggerApplication)
}