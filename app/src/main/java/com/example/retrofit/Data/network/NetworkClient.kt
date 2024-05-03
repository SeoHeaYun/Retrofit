package com.example.retrofit.Data.network

import com.example.retrofit.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

// api-retrogit-gson 연결

object NetWorkClient {

    private const val DATA_URL = "https://dapi.kakao.com/v2/search/image"

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
    private val dustRetrofit = Retrofit.Builder()
            .baseUrl(DATA_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(createOkHttpClient())
            .build()

    // interface 통해 사용할 것
    val dataNetWork: NetworkInterface = dustRetrofit.create(NetworkInterface::class.java)

}