package com.agarcia.myfirstandroidapp.data.repository.FavoriteMovie

import com.agarcia.myfirstandroidapp.data.database.dao.FavoriteMovieDao
import com.agarcia.myfirstandroidapp.data.database.entities.toDomain
import com.agarcia.myfirstandroidapp.data.model.FavoriteMovie
import com.agarcia.myfirstandroidapp.data.model.toDatabase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class FavoriteMovieRepositoryImpl(
  private val favoriteMovieDao: FavoriteMovieDao,
): FavoriteMovieRepository {

  override fun getFavoritesMovies(): Flow<List<FavoriteMovie>> {
    return favoriteMovieDao.getFavoritesMovies().map { list ->
      list.map { it.toDomain() }
    }
  }

  override fun isFavorite(movieId: Int): Flow<Boolean> {
    return favoriteMovieDao.getFavoriteMovieById(movieId).map{
      it != null
    }
  }
  override suspend fun addMovieToFavorites(movie: FavoriteMovie) {
    favoriteMovieDao.addMovieToFavorites(movie.toDatabase())
  }

  override suspend fun removeMovieFromFavorites(movie: FavoriteMovie) {
    favoriteMovieDao.removeMovieFromFavorites(movie.toDatabase())
  }
}