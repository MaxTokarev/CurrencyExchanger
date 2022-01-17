package com.maksimilian.currencyexchanger.data.network.core

import okhttp3.Interceptor
import okhttp3.Response

/**
 * Interceptor that automatically adds access key to query params
 *
 * @param accessKey is access key for the server url
 */
class AccessKeyInterceptor(private val accessKey: String) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val newHttpUrl = originalRequest.url().newBuilder()
            .addQueryParameter(ACCESS_KEY_QUERY_PARAM, accessKey)
            .build()
        val request = originalRequest.newBuilder()
            .url(newHttpUrl)
            .build()
        return chain.proceed(request)
    }

    companion object {
        private const val ACCESS_KEY_QUERY_PARAM = "access_key"
    }
}