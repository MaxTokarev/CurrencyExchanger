package com.maksimilian.currencyexchanger.di.module

import com.google.gson.Gson
import com.maksimilian.currencyexchanger.data.network.api.CurrencyApiService
import com.maksimilian.currencyexchanger.data.network.core.AccessKeyInterceptor
import com.maksimilian.currencyexchanger.data.network.core.ServerSettings
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class NetworkModule(private val serverSettings: ServerSettings) {

    @Provides
    @Singleton
    fun currencyApiService(retrofit: Retrofit): CurrencyApiService =
        retrofit.create(CurrencyApiService::class.java)

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create(Gson()))
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .baseUrl(serverSettings.url)
        .client(okHttpClient)
        .build()

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(AccessKeyInterceptor(serverSettings.accessKey))
        .build()
}