package com.example.projectmovie.repository

import com.example.projectmovie.network.RetrofitInstance

class MovieRepository {

    suspend fun searchMovie(
        searchQuery : String,
        pageNumber : Int
    ) = RetrofitInstance.api.searchMovies( searchQuery, pageNumber)

}