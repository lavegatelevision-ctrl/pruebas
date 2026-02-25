package com.lavegatelevision.tv.model

import com.google.gson.annotations.SerializedName

data class WpPost(
    val id: Int,
    val type: String = "post",
    val date: String = "",
    val title: WpRendered = WpRendered(""),
    val excerpt: WpRendered = WpRendered(""),
    val content: WpRendered = WpRendered(""),
    @SerializedName("link") val postUrl: String = ""
)

data class WpRendered(
    @SerializedName("rendered") val rendered: String = ""
)

data class WpSearchItem(
    val id: Int,
    val subtype: String,
    val title: String,
    val url: String
)
