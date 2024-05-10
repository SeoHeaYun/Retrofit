package com.example.retrofit.Data.network
import com.example.retrofit.Data.entity.kakaoDTO
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface kakaoAPI {
    @GET("search/image") // 요청주소
    fun getNetworkData(
        @Header("Authorization") auth : String, // authkey
        @Query("query") query : String // 요청값(key,value)
    ): Call<kakaoDTO> // entity 타입으로 반환
}