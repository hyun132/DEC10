package com.example.dec10.db

import android.app.Application
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.dec10.models.MovieModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Database(entities = arrayOf(MovieModel::class),version = 1)
abstract class MovieDatabase:RoomDatabase() {
    abstract fun savedMovieDao():MovieDao


    private class AppDatabaseCallback(
        private val scope: CoroutineScope
    ) : RoomDatabase.Callback() {

        override fun onOpen(db: SupportSQLiteDatabase) {
            super.onOpen(db)
            DB_INSTANCE?.let { database ->
                scope.launch {
                    var movieDao = database.savedMovieDao()

                }
            }
        }
    }


    companion object{
        @Volatile
        private var DB_INSTANCE:MovieDatabase?=null

        fun getDatabase(context: Context,scope:CoroutineScope):MovieDatabase{
            val tempInstance = DB_INSTANCE
            if (tempInstance!=null){
                return tempInstance
            }
            synchronized(this){
                val instance = Room.databaseBuilder(context.applicationContext,
                MovieDatabase::class.java,
                "savedmovies").build()
                DB_INSTANCE=instance
                return instance
            }
        }
    }
}
