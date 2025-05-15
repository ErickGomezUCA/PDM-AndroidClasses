package com.agarcia.myfirstandroidapp.data.repository.FavoriteMovie

import kotlinx.coroutines.flow.Flow

interface FavoriteMovieRepository {
  fun getFavoritesMovies(): Flow<List<FavoriteMovieRepository>>
  fun isFavorite(movieId: Int): Flow<Boolean>
  suspend fun addMovieToFavorites(movie: FavoriteMovieRepository)
  suspend fun removeMovieFromFavorites(movie: FavoriteMovieRepository)
}