package com.example.articlesreign._model.networking

import com.example.articlesreign._model.remote.ArticlesResponseServer
import retrofit2.http.GET

interface ArticlesApi {


    @GET("v1/search_by_date?query=android")
    suspend fun getArticlesApi(): ArticlesResponseServer
}