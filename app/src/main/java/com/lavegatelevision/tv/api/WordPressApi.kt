package com.lavegatelevision.tv.api

import com.lavegatelevision.tv.model.WpPost
import com.lavegatelevision.tv.model.WpSearchItem
import retrofit2.http.GET
import retrofit2.http.Query

interface WordPressApi {
    @GET("wp-json/wp/v2/posts")
    suspend fun getPosts(
        @Query("per_page") perPage: Int = 20,
        @Query("orderby") orderBy: String = "date",
        @Query("order") order: String = "desc",
        @Query("_embed") embed: Boolean = true
    ): List<WpPost>

    @GET("wp-json/wp/v2/pages")
    suspend fun getPages(
        @Query("per_page") perPage: Int = 20,
        @Query("orderby") orderBy: String = "date",
        @Query("order") order: String = "desc",
        @Query("_embed") embed: Boolean = true
    ): List<WpPost>

    @GET("wp-json/wp/v2/search")
    suspend fun searchContent(
        @Query("type") type: String = "post",
        @Query("subtype") subtype: String = "any",
        @Query("per_page") perPage: Int = 50
    ): List<WpSearchItem>
}
