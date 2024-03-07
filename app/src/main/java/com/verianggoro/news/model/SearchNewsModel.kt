package com.verianggoro.news.model

import com.google.gson.annotations.SerializedName

data class SearchNewsModel(
    @SerializedName("articles") var results : List<Articles>? = null
)
