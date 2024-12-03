package com.example.projectmovie.network

import com.example.projectmovie.models.MovieResponse
import com.example.projectmovie.utils.Constants.Companion.API_KEY
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface APIService {

    @GET("?apikey=3257c5ab&type=movie")
    suspend fun searchMovies(
     @Query("s")
     s: String,
     @Query("page")
     pageNumber: Int
    ): Response<MovieResponse>

}