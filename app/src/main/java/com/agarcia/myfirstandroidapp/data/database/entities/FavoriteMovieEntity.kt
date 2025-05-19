package com.agarcia.myfirstandroidapp.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.agarcia.myfirstandroidapp.data.model.FavoriteMovie

@Entity(tableName = "favorite_movies")
data class FavoriteMovieEntity (
  @PrimaryKey(autoGenerate = true)
  val id: Int = 0,
  val movieId: Int,
  val title: String,
  val posterUrl: String
)

// Convert FavoriteMovieEntity to FavoriteMovie
fun FavoriteMovieEntity.toDomain(): FavoriteMovie {
  return FavoriteMovie(
    id = id,
    movieId = movieId,
    title = title,
    posterUrl = posterUrl
  )
}
