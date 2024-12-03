package com.example.projectmovie.ui.activity


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.SearchView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.projectmovie.databinding.ActivityMainBinding
import com.example.projectmovie.network.NetworkHelper
import com.example.projectmovie.network.Resource
import com.example.projectmovie.repository.MovieRepository
import com.example.projectmovie.ui.adapter.MovieAdapter
import com.example.projectmovie.viewmodel.MovieViewModel
import com.example.projectmovie.viewmodel.MovieViewModelFactory
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding
    private lateinit var movieAdapter : MovieAdapter
   // var movieViewModel : MovieViewModel
   private val networkHelper = NetworkHelper(this)


    val movieViewModel : MovieViewModel by viewModels {
        MovieViewModelFactory(networkHelper, MovieRepository())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.emptyView.visibility = View.VISIBLE
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        movieAdapter = MovieAdapter()
        binding.recyclerView.adapter = movieAdapter

        movieViewModel.movies.observe(this, Observer { resource ->
            when(resource){
                is Resource.Loading -> {
                    binding.progressbar.visibility = View.VISIBLE
                    binding.emptyView.visibility = View.GONE
                }
                is Resource.Success -> {
                    binding.progressbar.visibility = View.GONE
                    binding.emptyView.visibility = View.GONE

                    movieAdapter.submitList(resource.data!!)

                }
                is Resource.Error -> {
                    binding.progressbar.visibility = View.GONE
                    Toast.makeText(this, resource.message, Toast.LENGTH_SHORT).show()
                    if(resource.message == "No Internet connection") {
                        resource.message.let { showSnackbar(it) }
                    }
                    else{
                        Toast.makeText(this, resource.message, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        })

        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let {
                    movieViewModel.searchMovies(it, 1)  // Pass the query and page number
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let {
                    if (it.length >= 3) {
                        movieViewModel.searchMovies(it, 1)  // Trigger the search after 3 characters
                    }
                }
                return true
            }

        })
    }
    private fun showSnackbar(message: String) {
        Snackbar.make(findViewById(android.R.id.content), message, Snackbar.LENGTH_LONG).also { snackbar ->
            snackbar.setAction("ok"){
                snackbar.dismiss()
            }

        }.show()
    }
}