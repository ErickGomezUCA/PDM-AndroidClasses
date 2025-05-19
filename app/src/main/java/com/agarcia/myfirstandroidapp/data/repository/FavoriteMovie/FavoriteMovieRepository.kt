package com.agarcia.myfirstandroidapp.data.repository.FavoriteMovie

import com.agarcia.myfirstandroidapp.data.model.FavoriteMovie
import kotlinx.coroutines.flow.Flow

interface FavoriteMovieRepository {
  fun getFavoritesMovies(): Flow<List<FavoriteMovie>>
  fun isFavorite(movieId: Int): Flow<Boolean>
  suspend fun addMovieToFavorites(movie: FavoriteMovie)
  suspend fun removeMovieFromFavorites(movie: FavoriteMovie)
}