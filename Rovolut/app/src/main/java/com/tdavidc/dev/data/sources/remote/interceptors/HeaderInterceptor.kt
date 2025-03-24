package com.tdavidc.dev.data.sources.remote.interceptors

import com.tdavidc.dev.BuildConfig
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class HeaderInterceptor: Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val requestBuilder: Request.Builder = request.newBuilder()

        requestBuilder.addHeader("Content-Type", "application/json")
        requestBuilder.addHeader("X-Jsio-Token", BuildConfig.API_KEY)

        return chain.proceed(requestBuilder.build())
    }
}