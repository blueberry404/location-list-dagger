package com.anum.locdagger.listeners

import io.reactivex.Scheduler

interface SchedulerProvider {
    fun ioScheduler() : Scheduler
    fun uiScheduler() : Scheduler
}