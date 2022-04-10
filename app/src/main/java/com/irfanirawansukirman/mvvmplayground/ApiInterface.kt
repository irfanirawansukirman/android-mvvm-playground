package com.irfanirawansukirman.mvvmplayground

import android.app.Application
import android.content.Context
import com.chuckerteam.chucker.api.Chucker
import com.chuckerteam.chucker.api.ChuckerCollector
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.concurrent.TimeUnit

interface ApiInterface {

    @GET("top-headlines")
    fun getTopHeadlines(
        @Query("country") country: String,
        @Query("apiKey") apiKey: String
    ): Call<TopHeadlinesResponse>

    companion object {

        @ExperimentalSerializationApi
        fun create(application: Application): ApiInterface {
            val client = OkHttpClient.Builder()
                .retryOnConnectionFailure(true)
                .pingInterval(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .connectTimeout(30, TimeUnit.SECONDS)
                .apply {
                    if (BuildConfig.DEBUG) {
                        addInterceptor(getChuckIntercept(application))
                        addInterceptor(getHttpLogIntercept())
                        addInterceptor(getChainIntercept())
                    }
                }.build()

            val contentType = "application/json".toMediaType()
            val retrofit = Retrofit.Builder()
                .client(client)
                .addConverterFactory(Json.asConverterFactory(contentType))
                .baseUrl(BuildConfig.BASE_URL)
                .build()
            return retrofit.create(ApiInterface::class.java)
        }

        private fun getChuckIntercept(appContext: Application) =
            ChuckerInterceptor.Builder(appContext)
                .collector(ChuckerCollector(appContext))
                .maxContentLength(250000L)
                .redactHeaders(emptySet())
                .alwaysReadResponseBody(false)
                .build()

        private fun getHttpLogIntercept() =
            HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

        private fun getChainIntercept() = { chain: Interceptor.Chain ->
            chain.proceed(
                chain.request().newBuilder()
                    .header("Content-Type", "application/json")
                    .header("Accept", "application/json")
                    .build()
            )
        }
    }
}