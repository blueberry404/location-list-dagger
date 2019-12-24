package com.anum.locdagger.service

import com.anum.locdagger.helpers.AppSchedulerProvider
import com.anum.locdagger.service.api.LocationService
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class NetworkModule {

    @Singleton
    @Provides fun getServiceManager(retrofit: Retrofit) : ServiceManager = ServiceManager(retrofit)

    @Singleton
    @Provides fun getRetrofit(okHttpClient: OkHttpClient, gson: Gson) : Retrofit {
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl("https://some-url.com")
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    @Provides fun getGson(): Gson {
        return GsonBuilder().setLenient().create()
    }

    @Provides fun getOkHttpClient(): OkHttpClient {
        var okHttpClient = OkHttpClient()
        return okHttpClient.newBuilder().build()
    }

    @Provides fun getLocationService(serviceManager: ServiceManager) : LocationService {
        return serviceManager.getService(LocationService::class.java) as LocationService
    }

    @Singleton
    @Provides
    fun provideAppSchedulerProvider() : AppSchedulerProvider = AppSchedulerProvider()
}