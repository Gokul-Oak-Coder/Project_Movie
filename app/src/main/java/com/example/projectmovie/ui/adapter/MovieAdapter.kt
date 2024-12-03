package com.example.projectmovie.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.AsyncDifferConfig
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.example.projectmovie.R
import com.example.projectmovie.models.Search

class MovieAdapter : RecyclerView.Adapter<MovieAdapter.MovieViewHolder>(){

    inner class MovieViewHolder(itemView : View): RecyclerView.ViewHolder(itemView) {

        val poster : AppCompatImageView = itemView.findViewById(R.id.poster)
        val title : AppCompatTextView = itemView.findViewById(R.id.title)
        val year : AppCompatTextView = itemView.findViewById(R.id.year)
        val id : AppCompatTextView = itemView.findViewById(R.id.id)
        val type : AppCompatTextView = itemView.findViewById(R.id.type)
    }

    private val differCallback = object : DiffUtil.ItemCallback<Search>(){
        override fun areItemsTheSame(oldItem: Search, newItem: Search): Boolean {
            return oldItem.imdbID == newItem.imdbID
        }
        override fun areContentsTheSame(oldItem: Search, newItem: Search): Boolean {
            return oldItem == newItem
        }

    }
    private val diff = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieAdapter.MovieViewHolder {
        return MovieViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false))
    }

    override fun onBindViewHolder(holder: MovieAdapter.MovieViewHolder, position: Int) {
        val movie = diff.currentList[position]

        holder.apply {
            if(movie.Poster != null && movie.Poster != "N/A"){
                Glide.with(itemView.context).load(movie.Poster).into(poster)
            }
            else{
                Glide.with(itemView.context).load(R.drawable.ic_launcher_foreground).into(poster)
            }
            title.text = movie.Title
            year.text = movie.Year
            id.text = movie.imdbID
            type.text = movie.Type

            itemView.setOnClickListener {
                onItemClickListener?.let { it(movie) }
            }
        }

    }

    override fun getItemCount(): Int {
        return diff.currentList.size
    }

    private var onItemClickListener : ((Search) -> Unit)? = null

    fun setOnItemClickListener(listener : (Search) -> Unit){
        onItemClickListener = listener

    }

    fun submitList(list: List<Search>) {
        diff.submitList(list)
    }

}