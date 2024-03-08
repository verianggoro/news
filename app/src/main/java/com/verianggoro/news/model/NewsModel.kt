package com.verianggoro.news.model

import com.google.gson.annotations.SerializedName

data class NewsModel (
    @SerializedName("articles") var results : ArrayList<Articles>? = null
)

data class Articles (
    @SerializedName("source") var source: Source? = null,
    @SerializedName("author") var author: String? = null,
    @SerializedName("title") var title: String? = null,
    @SerializedName("description") var description: String? = null,
    @SerializedName("url") var url: String? = null,
    @SerializedName("urlToImage") var urlImg: String? = null,
    @SerializedName("publishedAt") var published: String? = null,
    @SerializedName("content") var content: String? = null
)

data class Source(
    @SerializedName("id") var idSource: String? = null,
    @SerializedName("name") var nameSource: String? = null
)