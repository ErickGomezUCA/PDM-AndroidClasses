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

fun FavoriteMovie.toMovie(): Movie {
    return Movie(
        id = movieId,
        title = title,
        originalTitle = title,
        originalLanguage = "en",
        overview = "",
        releaseDate = "",
        adult = false,
        genreIds = emptyList(),
        popularity = 0.0,
        voteAverage = 0.0,
        voteCount = 0,
        video = false,
        backdropUrl = "",
        posterUrl = posterUrl
    )
}