package com.lavegatelevision.tv.api

import com.lavegatelevision.tv.model.WpPost
import retrofit2.http.GET
import retrofit2.http.Query

interface WordPressApi {
    @GET("wp-json/wp/v2/posts")
    suspend fun getPosts(
        @Query("per_page") perPage: Int = 20,
        @Query("_embed") embed: Boolean = true
    ): List<WpPost>
}
