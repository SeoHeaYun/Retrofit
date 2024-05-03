package com.example.retrofit.Data.network

import com.example.retrofit.Data.entity.ResponceData
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface NetworkInterface {
    @GET("https://dapi.kakao.com/v2/search/image") // 요청URL
    suspend fun getData(@QueryMap param: HashMap<String,String>):ResponceData   // 요청값(key,value)
}