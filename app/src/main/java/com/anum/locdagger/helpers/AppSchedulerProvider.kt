package com.anum.locdagger.helpers

import com.anum.locdagger.listeners.SchedulerProvider
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class AppSchedulerProvider : SchedulerProvider {

    override fun ioScheduler(): Scheduler = Schedulers.io()

    override fun uiScheduler(): Scheduler = AndroidSchedulers.mainThread()

}