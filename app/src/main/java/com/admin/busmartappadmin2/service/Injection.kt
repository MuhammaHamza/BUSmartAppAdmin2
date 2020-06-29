package com.admin.busmartappadmin2.service

import com.admin.busmartappadmin2.BuildConfig

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class Injection {
    companion object {
        private fun getOkHttpClient(): OkHttpClient {
            val logger = HttpLoggingInterceptor()
            if (BuildConfig.DEBUG)
                logger.level = HttpLoggingInterceptor.Level.BODY
            else
                logger.level = HttpLoggingInterceptor.Level.NONE

            return OkHttpClient.Builder()
                .connectTimeout(240, TimeUnit.SECONDS)
                .readTimeout(240, TimeUnit.SECONDS)
                .writeTimeout(240, TimeUnit.SECONDS)
                .followRedirects(false)
                .addInterceptor(logger)
                .build()
        }

        private fun provideRetrofit(): Retrofit {
            val httpClient =
                getOkHttpClient()

            return Retrofit.Builder()
                .baseUrl(API.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient)
                .build()
        }

        fun provideApiService(): ApiService {
            return provideRetrofit()
                .create(ApiService::class.java)
        }
    }
}