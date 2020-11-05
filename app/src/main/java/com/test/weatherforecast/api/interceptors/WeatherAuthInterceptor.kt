package com.test.weatherforecast.api.interceptors

import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class WeatherAuthInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        var request: Request = chain.request()
        val url: HttpUrl = request.url()
            .newBuilder()
            .addQueryParameter("key", "8fd6ec48437c4a8ca3e143845200411")
            .build()
        request = request.newBuilder().url(url).build()
        return chain.proceed(request)
    }
}