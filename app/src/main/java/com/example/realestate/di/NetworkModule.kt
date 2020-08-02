package com.example.realestate.di

import com.example.realestate.Const
import com.example.realestate.api.PropertyService
import com.example.realestate.util.LiveDataCallAdapterFactory
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class NetworkModule {

    @Singleton
    @Provides
    fun providePropertyService(): PropertyService {
        return Retrofit.Builder()
            .baseUrl(Const.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(LiveDataCallAdapterFactory())
            .client(OkHttpClient())
            .build()
            .create(PropertyService::class.java)
    }

}