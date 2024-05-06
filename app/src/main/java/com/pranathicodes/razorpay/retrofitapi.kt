package com.pranathicodes.razorpay

import android.util.Log
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.TimeZone
import java.util.concurrent.TimeUnit

object RetrofitApi {
    var retrofitServ: Razorpayapi?

    init {
        retrofitServ = null
        try {
            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("https://api.razorpay.com/v1/")
                .client(HttpClientWithIntercept.client)
                .build()
            retrofitServ = retrofit.create(Razorpayapi::class.java)

        } catch (e: Exception) {
            Log.d("exception", "$e")
        }
    }
}

object HttpClientWithIntercept {
    val client: OkHttpClient = OkHttpClient.Builder()
        .connectTimeout(40, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .addInterceptor(AuthInterceptor)
        .addInterceptor(LoggingInterceptor)
        .build()

}


object LoggingInterceptor : Interceptor {
    private val logger = HttpLoggingInterceptor.Logger { message ->
        Log.d("razorpay", message)
    }

    private val loggingInterceptor = HttpLoggingInterceptor(logger).apply {
        level = HttpLoggingInterceptor.Level.BODY // You can change the log level as needed
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        return loggingInterceptor.intercept(chain)
    }
}

object AuthInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()

        val modifiedRequest =
            originalRequest.newBuilder()
                .header("platform", "google")
                .header("Content-Type", "application/json")
                .header("Accept", "application/json; charset=utf-8")
                .header("TimeZone", TimeZone.getDefault().id)
                .build()

        return chain.proceed(modifiedRequest)
    }
}



