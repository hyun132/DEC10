package com.example.dec10.response

import androidx.lifecycle.MutableLiveData
import com.example.dec10.models.MovieModel
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

//결과가 많을 때 -> movie List
class MovieSearchResponse {

    //response 객체 내에 내가 사용할 영화 목록들이 담긴 배열의 key값 = results
    @SerializedName("total_results")
    @Expose
    var total_count:Int = 0

    //response 객체 내에 내가 사용할 영화 목록들이 담긴 배열의 key값 = results
    @SerializedName("results")
    @Expose
    lateinit var movieList: List<MovieModel>

}