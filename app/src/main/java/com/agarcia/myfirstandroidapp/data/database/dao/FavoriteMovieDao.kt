package com.agarcia.myfirstandroidapp.data.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.agarcia.myfirstandroidapp.data.database.entities.FavoriteMovieEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteMovieDao {

  @Query("SELECT * FROM favorite_movies")
  fun getFavoritesMovies(): Flow<List<FavoriteMovieEntity>>

  @Query("SELECT * FROM favorite_movies WHERE movieId = :movieId")
  fun getFavoriteMovieById(movieId: Int): Flow<FavoriteMovieEntity?>

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun addMovieToFavorites(movie: FavoriteMovieEntity)

  @Delete
  suspend fun removeMovieFromFavorites(movie: FavoriteMovieEntity)
}