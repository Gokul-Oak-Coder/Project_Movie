package com.example.projectmovie.models

data class MovieResponse(
    val Response: String,
    val Search: List<Search>,
    val totalResults: String
)