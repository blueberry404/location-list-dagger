package com.anum.locdagger.service

import retrofit2.Retrofit
import java.util.concurrent.ConcurrentHashMap
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ServiceManager @Inject constructor(var retrofit: Retrofit) {

    private var serviceCache : ConcurrentHashMap<String, Any> = ConcurrentHashMap()

     @Synchronized fun <S> getService(cls: Class<S>): Any? {

         if(serviceCache?.contains(cls.toString())) {
             return serviceCache[cls.toString()] as S?
         }

         var serv : S? = retrofit.create(cls) as S
         serviceCache[cls.toString()] = serv as Any
         return serv
    }

}