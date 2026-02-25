package com.lavegatelevision.tv.model

import com.google.gson.annotations.SerializedName

data class WpPost(
    val id: Int,
    val date: String,
    val title: WpRendered,
    val excerpt: WpRendered,
    val content: WpRendered,
    @SerializedName("link") val postUrl: String
)

data class WpRendered(
    @SerializedName("rendered") val rendered: String
)
