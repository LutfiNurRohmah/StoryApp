package com.lutfi.storyapp.data.api.retrofit

import android.content.Context
import com.lutfi.storyapp.data.pref.UserPreference
import com.lutfi.storyapp.data.pref.dataStore
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor(private val context: Context): Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val requestBuilder = chain.request().newBuilder()
        val pref = UserPreference.getInstance(context.dataStore)
        val token = runBlocking { pref.getSession().first().token }
        token?.let {
            requestBuilder.addHeader("Authorization", "Bearer $it")
        }
        val request = requestBuilder.build()
        return chain.proceed(request)
    }

}