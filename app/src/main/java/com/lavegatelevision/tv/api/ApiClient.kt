package com.lavegatelevision.tv.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {
    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl("https://lavegatelevision.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val wordPressApi: WordPressApi = retrofit.create(WordPressApi::class.java)
}
