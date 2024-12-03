package com.example.projectmovie.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.projectmovie.network.NetworkHelper
import com.example.projectmovie.repository.MovieRepository

class MovieViewModelFactory(private val networkHelper: NetworkHelper, private val repository: MovieRepository) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(MovieViewModel::class.java)){
            return MovieViewModel(networkHelper, repository) as T
        }

        throw IllegalArgumentException("unknown viewmodel class")
    }

   /* class LoginViewModelFactory( private val movieRepository: MovieRepository): ViewModelProvider.Factory{
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
                return LoginViewModel(networkHelper,loginRepository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }*/
}