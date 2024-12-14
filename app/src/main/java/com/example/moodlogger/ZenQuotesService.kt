package com.example.moodlogger

import retrofit2.Call
import retrofit2.http.GET

interface ZenQuotesService {
    @GET("api/random")
    fun getQuote(): Call<List<QuoteResponse>>
}

data class QuoteResponse(
    val q: String,
    val a: String
)