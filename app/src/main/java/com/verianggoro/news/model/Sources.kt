package com.verianggoro.news.model

import com.google.gson.annotations.SerializedName

data class Sources(
    @SerializedName("sources") var results : List<Resource>? = null
)

data class Resource(
    @SerializedName("id") var id : String? = null,
    @SerializedName("name") var name : String? = null,
    @SerializedName("description") var description : String? = null,
    @SerializedName("url") var url : String? = null
)
