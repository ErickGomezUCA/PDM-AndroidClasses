package com.agarcia.myfirstandroidapp.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_movies")
data class FavoriteMovieEntity (
  @PrimaryKey(autoGenerate = true)
  val id: Int = 0,
  val movieId: Int,
  val title: String,
  val posterUrl: String
)
