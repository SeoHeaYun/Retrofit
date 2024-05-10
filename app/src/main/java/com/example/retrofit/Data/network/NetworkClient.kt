package com.example.retrofit.Data.network

import com.example.retrofit.BuildConfig
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.internal.bind.util.ISO8601Utils
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.NetworkInterface
import java.sql.Time
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.concurrent.TimeUnit

// api-retrogit-gson 연결

object NetWorkClient {

    private const val DATA_URL = "https://dapi.kakao.com/v2/" // baseUrl(End-Point)

    // OkHttp
    private fun createOkHttpClient(): OkHttpClient {
        // 통신 debug를 위한 logging
        val interceptor = HttpLoggingInterceptor()
        if (BuildConfig.DEBUG)
            interceptor.level = HttpLoggingInterceptor.Level.BODY // 활성화
        else
            interceptor.level = HttpLoggingInterceptor.Level.NONE // 비활성화
        // 시간 제한 옵션
        return OkHttpClient.Builder()
            .connectTimeout(20, TimeUnit.SECONDS) // 연결
            .readTimeout(20, TimeUnit.SECONDS) // 읽기
            .writeTimeout(20, TimeUnit.SECONDS) // 쓰기
            .addNetworkInterceptor(interceptor) // log 추가
            .build() // 최종반환
    }

    // retrofit & GSON converter
        // datetime 포맷 변경
    private val gson : Gson = GsonBuilder()
        .setDateFormat("yyyy-MM-dd HH:mm:ss")
        .create()

    private val dataRetrofit = Retrofit
            .Builder()
            .baseUrl(DATA_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(createOkHttpClient())
            .build()

    // Service: client(레트로핏)와 interface(요청) 연결
    val networkService : kakaoAPI  = dataRetrofit.create(kakaoAPI::class.java)

}