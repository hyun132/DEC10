package com.example.dec10.api

import com.example.dec10.response.MovieResponse
import com.example.dec10.response.MovieSearchResponse
import com.example.dec10.utils.Constants.Companion.API_KEY
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

//https://api.themoviedb.org/3/movie/popular 인기
//https://api.themoviedb.org/3/movie/search
interface RetrofitApi {

    @GET("movie/popular")
    fun getPopularMovie(
        @Query("api_key") api_key:String= API_KEY,
    ): Call<MovieSearchResponse>

    @GET("movie/upcoming")
    fun getUpcomingMovie(
        @Query("api_key") api_key:String= API_KEY,
    ): Call<MovieSearchResponse>

    @GET("/search/movie")
    fun searchMovie(
        @Query("query") query: String="",
        @Query("api_key") api_key:String= API_KEY,
        @Query("page") page: Int
    ): Call<MovieSearchResponse>

    @GET("{movie_id}")
    fun getMovie(
        @Path("movie_id") movie_id : Int,
        @Query("api_key") api_key : String = API_KEY
    ):Call<MovieResponse>

}