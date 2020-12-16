package com.example.dec10.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.room.RoomDatabase
import com.example.dec10.db.MovieDao
import com.example.dec10.db.MovieDatabase
import com.example.dec10.models.MovieModel
import com.example.dec10.request.RetrofitInstance

class MovieRepository(val dao: MovieDao) {

    suspend fun getPopularMovies()=
        RetrofitInstance.getMovieApi().getPopularMovie()

    suspend fun getUpcomingMovies()=
        RetrofitInstance.getMovieApi().getUpcomingMovie()

    suspend fun upsert(movie:MovieModel){
        Log.d("upsert() :",movie.title.toString())
        dao.upsert(movie)
    }

    suspend fun search(searchText:String) = RetrofitInstance.getMovieApi().searchMovie(query = searchText,page = 1)

    suspend fun delete(movie: MovieModel){dao.deleteMovie(movie)}

    val AllSavedMovie:LiveData<MutableList<MovieModel>> = dao.getAllSavedMovie()
}