package com.example.projectmovie.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.projectmovie.models.Search
import com.example.projectmovie.network.NetworkHelper
import com.example.projectmovie.network.Resource
import com.example.projectmovie.repository.MovieRepository
import kotlinx.coroutines.launch

class MovieViewModel(
    private val networkHelper: NetworkHelper,
    private val repository: MovieRepository
): ViewModel() {

    private val _movies = MutableLiveData<Resource<List<Search>>>()
    val movies : LiveData<Resource<List<Search>>> = _movies

    fun searchMovies(searchQuery: String, pageNumber : Int){
        _movies.value = Resource.Loading()
        if(networkHelper.isNetworkAvailable()) {
            viewModelScope.launch {
                val response = repository.searchMovie(searchQuery, pageNumber)

                try{
                    if (response.isSuccessful) {
                        response.body()?.let {
                            _movies.value = Resource.Success(it.Search)
                        } ?: run {
                            _movies.value = Resource.Error("No available data")
                            _movies.value = Resource.Success(emptyList())
                        }
                    } else {
                        _movies.value = Resource.Error("Error: ${response.message()}")
                        _movies.value = Resource.Success(emptyList())
                    }
                }
                catch (e: Exception){
                    _movies.value = Resource.Error("$e")
                    _movies.value = Resource.Success(emptyList())
                }
            }
        }
        else{
            _movies.value = Resource.Error("No Internet")
        }
    }

}