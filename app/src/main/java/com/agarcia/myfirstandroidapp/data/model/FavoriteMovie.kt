package com.agarcia.myfirstandroidapp.data.model

import com.agarcia.myfirstandroidapp.data.database.entities.FavoriteMovieEntity

data class FavoriteMovie (
    val id: Int,
    val movieId: Int,
    val title: String,
    val posterUrl: String,
)

// Convert FavoriteMovie to FavoriteMovieEntity
fun FavoriteMovie.toDatabase(): FavoriteMovieEntity {
    return FavoriteMovieEntity(
        id = id,
        movieId = movieId,
        title = title,
        posterUrl = posterUrl
    )
}