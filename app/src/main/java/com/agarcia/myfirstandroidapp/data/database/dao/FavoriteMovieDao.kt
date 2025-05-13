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
    // User Flow para tener cambios en tiempo real
    @Query("SELECT * FROM Favorite_Movie")
    fun getFavoriteMovies(): Flow<List<FavoriteMovieEntity>>

    @Query("SELECT * FROM Favorite_Movie WHERE movieId = :id")
    fun getFavoriteMovieById(id: Int): Flow<FavoriteMovieEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    //  Insert y Delete se le pasa todo el objeto, asi funciona el decorador
    //  Strategy para realizar la accion en que exista un conflicto
    //  suspend porque es una funcion asincronica. No se le asigna Flow porque no devuelve ningun dato
    suspend fun addMovieToFavorite(movie: FavoriteMovieEntity)

    @Delete
    suspend fun removeMovieFromFavorites(movie: FavoriteMovieEntity)
}