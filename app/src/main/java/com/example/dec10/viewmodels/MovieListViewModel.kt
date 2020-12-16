package com.example.dec10.viewmodels

import android.app.Application
import android.graphics.Movie
import android.util.Log
import androidx.lifecycle.*
import com.example.dec10.db.MovieDatabase
import com.example.dec10.models.MovieModel
import com.example.dec10.repository.MovieRepository
import com.example.dec10.response.MovieResponse
import com.example.dec10.response.MovieSearchResponse
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException

class MovieListViewModel(app:Application): AndroidViewModel(app) {
//    val breakingNews:MutableLiveData<Resource<NewsResponse>> = MutableLiveData()
    lateinit var popularMovieList:List<MovieModel>
    lateinit var popularMovieResponse : MovieSearchResponse

    lateinit var upComingMovieList:List<MovieModel>

    lateinit var searchMovieList:List<MovieModel>
//    var savedMovieList:LiveData<MutableList<MovieModel>>
    private val movieRepository:MovieRepository

    init {
        val movieDao=MovieDatabase.getDatabase(app,viewModelScope).savedMovieDao()
        movieRepository= MovieRepository(movieDao)
//        savedMovieList = movieRepository.AllSavedMovie
        getPopularMovies()
        getUpcomingMovies()
    }

    fun saveMovie(movie:MovieModel)=viewModelScope.launch {
        movieRepository.upsert(movie)

        Log.d("saveMovie() :",movie.title.toString())
    }

    fun deleteSavedMovie(movie:MovieModel) = viewModelScope.launch {
        movieRepository.delete(movie)
    }

    fun searchMovie(string:String)=viewModelScope.launch {
        movieRepository.search(string).enqueue(object : Callback<MovieSearchResponse> {
            override fun onResponse(
                call: Call<MovieSearchResponse>,
                response: Response<MovieSearchResponse>
            ) {
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        searchMovieList=responseBody.movieList
                    } else {
                        Log.d("Repository", "Failed to get response")
                    }
                }
            }

            override fun onFailure(call: Call<MovieSearchResponse>, t: Throwable) {
                Log.e("Repository", "onFailure", t)
            }
        }
        )
    }


    fun getSavedMovies()=movieRepository.AllSavedMovie
    fun getPopularMovies()=viewModelScope.launch {
        movieRepository.getPopularMovies() .enqueue(object : Callback<MovieSearchResponse> {
            override fun onResponse(
                call: Call<MovieSearchResponse>,
                response: Response<MovieSearchResponse>
            ) {
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        popularMovieList=responseBody.movieList
                    } else {
                        Log.d("Repository", "Failed to get response")
                    }
                }
            }

            override fun onFailure(call: Call<MovieSearchResponse>, t: Throwable) {
                Log.e("Repository", "onFailure", t)
            }
        }
        )
    }

    fun getUpcomingMovies()=viewModelScope.launch {
        movieRepository.getUpcomingMovies() .enqueue(object : Callback<MovieSearchResponse> {
            override fun onResponse(
                call: Call<MovieSearchResponse>,
                response: Response<MovieSearchResponse>
            ) {
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        upComingMovieList=responseBody.movieList
                    }
                }
            }
            override fun onFailure(call: Call<MovieSearchResponse>, t: Throwable) {
            }
        }
        )
    }


//    private fun getRetrofitResponse(){
//        val movieApi = RetrofitInstance.getMovieApi()
//
//        val responseCall: Call<MovieSearchResponse> = movieApi.getPopularMovie()
//
//        responseCall.enqueue(object :Callback<MovieSearchResponse>{
//            override fun onResponse(call: Call<MovieSearchResponse>, response: Response<MovieSearchResponse>) {
//                if (response.code()==200){
//                    Log.v("Tag","the response: ${response.body().toString()}")
//
//                    movieData = response.body()!!.movieList as MutableList<MovieModel>
//
//                    for (m in  movieData){
//                        Log.v("Tag","The title ${m.title.toString()}")
//                    }
//                }else{
//                    try {
//                        Log.v("TAG","Error : ${response.errorBody().toString()}")
//                    }catch (e:IOException){
//                        e.printStackTrace()
//                    }
//                }
//            }
//
//            override fun onFailure(call: Call<MovieSearchResponse>, t: Throwable) {
//
//            }
//
//        })
//    }


}