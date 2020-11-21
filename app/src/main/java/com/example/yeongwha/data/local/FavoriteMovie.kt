package com.example.yeongwha.data.local

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize
import java.io.Serializable

@Entity(tableName = "favorite_movie")
@Parcelize
data class FavoriteMovie(
    var id_movie: Int,
    val poster_path: String,
    val releaseDate: String,
    val original_title: String
) : Serializable, Parcelable {
    @PrimaryKey(autoGenerate = true)
    var id : Int = 0
}