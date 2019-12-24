package com.anum.locdagger.di.app

import android.app.Application
import android.content.Context
import dagger.Module
import javax.inject.Singleton
import dagger.Provides

@Module
class AppModule constructor(app: Application) {
    private var application: Application = app

    @Provides
    @Singleton
    fun provideContext(): Context {
        return application
    }

}