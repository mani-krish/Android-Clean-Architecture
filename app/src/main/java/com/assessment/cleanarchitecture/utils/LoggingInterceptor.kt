package com.assessment.cleanarchitecture.utils

import android.util.Log
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

/*Log the request and response using interceptor*/
class LoggingInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request: Request = chain.request()
        val response: Response = chain.proceed(request)
        Log.d("request-url", request.url.toString())
        Log.d("request-headers", request.headers.toString())
        Log.d("request-body", request.body.toString())
        Log.d("request-response-body", response.body.toString())
        Log.d("request-response-code", response.code.toString())
        return response
    }
}