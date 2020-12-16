package com.example.dec10.models

import android.os.Parcel
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlin.collections.ArrayList

class MovieList :ArrayList<MovieModel>()


@Entity(tableName = "savedmovies")
data class MovieModel(
    @PrimaryKey (autoGenerate = true)
    val movie_id:Int,
    val title :String?,
    val poster_path :String?,
    val release_date :String?,
    val vote_average:Float,
    val overview:String?
):Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readFloat(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(title)
        parcel.writeString(poster_path)
        parcel.writeString(release_date)
        parcel.writeInt(movie_id)
        parcel.writeFloat(vote_average)
        parcel.writeString(overview)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<MovieModel> {
        override fun createFromParcel(parcel: Parcel): MovieModel {
            return MovieModel(parcel)
        }

        override fun newArray(size: Int): Array<MovieModel?> {
            return arrayOfNulls(size)
        }
    }
}