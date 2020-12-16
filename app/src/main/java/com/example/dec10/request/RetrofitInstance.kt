package com.example.dec10.request

import com.example.dec10.api.RetrofitApi
import com.example.dec10.utils.Constants.Companion.BASE_URL
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitInstance {

    companion object {
        fun getMovieApi(): RetrofitApi {

            val retrofit = Retrofit.Builder()
                .baseUrl("https://api.themoviedb.org/3/")
                .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create())) //Fratory, Builder.. 다 java 디자인 패턴?
                .client(OkHttpClient())
                .build()


            return retrofit.create(RetrofitApi::class.java)

        }

    }

}