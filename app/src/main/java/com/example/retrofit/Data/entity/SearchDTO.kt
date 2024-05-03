package com.example.retrofit.Data.entity

import com.google.gson.annotations.SerializedName

// 응답받는 형식(database 구조)
data class ResponceData(
    @SerializedName("Meta")
    val meta: Meta,
    @SerializedName("Documents")
    val documents: MutableList<Document>
)
data class Meta (
    val total_count: Int,
    val pegeable_count: Int,
    val is_ent: Boolean
)

data class Document (
    val collection: String,
    val tumbnail_url: String,
    val image_url: String,
    val width: String,
    val height: Int,
    val display_sitename: String,
    val doc_url: String,
    val datetime: String
)

