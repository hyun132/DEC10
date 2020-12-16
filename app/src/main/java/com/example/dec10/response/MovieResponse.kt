package com.example.dec10.response

import com.example.dec10.models.MovieModel
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

//영화 한개만 요청할때
class MovieResponse {

    @SerializedName("results")
    @Expose
    lateinit var movieModel:MovieModel




}