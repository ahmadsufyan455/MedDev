package com.capstone.meddev.service

import com.capstone.meddev.data.ArticleResponse
import retrofit2.http.GET

interface ApiService {
    @GET("article")
    suspend fun getArticle(): ArticleResponse
}