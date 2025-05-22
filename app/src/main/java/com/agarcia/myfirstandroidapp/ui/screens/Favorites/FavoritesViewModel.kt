package com.agarcia.myfirstandroidapp.ui.screens.Favorites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.agarcia.myfirstandroidapp.MyFirstAndroidAppAplication
import com.agarcia.myfirstandroidapp.data.model.FavoriteMovie
import com.agarcia.myfirstandroidapp.data.repository.FavoriteMovie.FavoriteMovieRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class FavoritesViewModel(
  private val favoriteMovieRepository: FavoriteMovieRepository
):ViewModel() {
  val favoriteMovies: Flow<List<FavoriteMovie>> = favoriteMovieRepository.getFavoritesMovies()

  fun isFavorite(movieId: Int): StateFlow<Boolean> {
    return favoriteMovieRepository.isFavorite(movieId)
      .stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = false
      )
  }

  fun toggleFavorite(movie: FavoriteMovie) {
    viewModelScope.launch {
      val isFavorite = favoriteMovieRepository.isFavorite(movie.id).first()

      if (isFavorite) {
        favoriteMovieRepository.removeMovieFromFavorites(movie)
      }
      else {
        favoriteMovieRepository.addMovieToFavorites(movie)
      }
    }
  }

  companion object {
    val Factory: ViewModelProvider.Factory = viewModelFactory {
      initializer {
        val application = (this[APPLICATION_KEY] as MyFirstAndroidAppAplication)
        FavoritesViewModel(application.appProvider.provideFavoriteMovieRepository())
      }
    }
}