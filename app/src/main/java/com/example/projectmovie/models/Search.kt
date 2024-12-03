package com.example.projectmovie.models

data class Search(
    val Poster: String? = null,
    val Title: String,
    val Type: String,
    val Year: String,
    val imdbID: String
)