package com.agarcia.myfirstandroidapp.data.repository.FavoriteMovie

import kotlinx.coroutines.flow.Flow

interface FavoriteMovie {
    fun getFavoriteMovies(): Flow<List<FavoriteMovie>>
    fun getFavoriteMovieById(id: Int): Flow<FavoriteMovie?>
    suspend fun addMovieToFavorite(movie: FavoriteMovie)
    suspend fun removeMovieFromFavorites(movie: FavoriteMovie)
}