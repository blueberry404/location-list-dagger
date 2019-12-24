package com.anum.locdagger

import com.anum.locdagger.listeners.SchedulerProvider
import io.reactivex.Scheduler
import io.reactivex.schedulers.Schedulers

class TestSchedulerProvider : SchedulerProvider {

    override fun ioScheduler(): Scheduler = Schedulers.trampoline()
    override fun uiScheduler(): Scheduler = Schedulers.trampoline()

}