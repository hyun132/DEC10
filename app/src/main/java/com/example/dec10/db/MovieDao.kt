package com.example.dec10.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.dec10.models.MovieModel


@Dao
interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(movie:MovieModel):Long

    @Delete
    suspend fun deleteMovie(movie: MovieModel)

    @Query("SELECT * from savedmovies")
    fun getAllSavedMovie():LiveData<MutableList<MovieModel>>
}