package com.example.dec10

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.dec10.models.MovieModel
import com.example.dec10.ui.MypageFragment
import kotlinx.android.synthetic.main.saved_movie_item.view.*

class SavedMovieAdapter internal constructor(context: MypageFragment) :
    RecyclerView.Adapter<SavedMovieAdapter.SavedMovieViewHolder>() {

//    private val inflater: LayoutInflater = LayoutInflater.from(context.context)

    private var savedMovieList= emptyList<MovieModel>()

    private val differCallback = object : DiffUtil.ItemCallback<MovieModel>() {
        //둘이 같은 객체인지
        override fun areItemsTheSame(oldItem: MovieModel, newItem: MovieModel): Boolean {
            return oldItem.movie_id == newItem.movie_id
        }

        //둘이 같은 아이템인지
        override fun areContentsTheSame(oldItem: MovieModel, newItem: MovieModel): Boolean {
            return oldItem == newItem
        }

    }

    val differ = AsyncListDiffer(this, differCallback)


    inner class SavedMovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val poster: ImageView = itemView.poster_image_iew

        fun bind(item:MovieModel){
            Log.d("SavedMovieViewHolder : "," ${savedMovieList.size}")
            Glide.with(itemView.context).load("https://image.tmdb.org/t/p/w500${item.poster_path}").into(poster)
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SavedMovieViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.saved_movie_item, parent, false)
        return SavedMovieViewHolder(view)
    }

    override fun onBindViewHolder(holder: SavedMovieViewHolder, position: Int) {
        val movie = differ.currentList[position]
        holder.bind(movie)
    }

    override fun getItemCount(): Int = differ.currentList.size


//
//    internal fun setSavedMovieList(data:MutableList<MovieModel>) {
//        savedMovieList = data
//        Log.d("sett : ",savedMovieList.size.toString())
////        notifyDataSetChanged()
//    }
//    internal suspend fun saveMovie(movie:MovieModel){
//        movieRepository.dao.upsert(movie)
//        notifyDataSetChanged()
//    }
private var onItemClickListener: ((MovieModel) -> Unit)? = null

    fun setOnItemClickListener(listener: (MovieModel) -> Unit) {
        onItemClickListener = listener
    }
}