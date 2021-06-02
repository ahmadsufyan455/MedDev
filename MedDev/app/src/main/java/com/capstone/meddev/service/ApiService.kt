package com.capstone.meddev.service

import com.capstone.meddev.data.ListArticleResponse
import com.capstone.meddev.data.NearbyPlacesResponse
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {
    @GET("article")
    suspend fun getArticle(): Response<ListArticleResponse>

    @GET("nearby")
    suspend fun getNearbyPlaces(): Response<NearbyPlacesResponse>
}