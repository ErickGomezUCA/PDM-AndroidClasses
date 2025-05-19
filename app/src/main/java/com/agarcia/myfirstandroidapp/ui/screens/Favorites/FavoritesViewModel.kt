package com.agarcia.myfirstandroidapp.ui.screens.Favorites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.agarcia.myfirstandroidapp.MyFirstAndroidAppAplication
import com.agarcia.myfirstandroidapp.data.repository.FavoriteMovie.FavoriteMovieRepository

class FavoritesViewModel(
  private val favoritesRepository: FavoriteMovieRepository,
): ViewModel() {

  fun isFavoriteMovie(movieId: Int): Boolean {
    //favoritesRepository.isFavorite(movieId)
    return true
  }

  companion object {
    val Factory: ViewModelProvider.Factory = viewModelFactory {
      initializer {
        val aplication = this[APPLICATION_KEY] as MyFirstAndroidAppAplication
        FavoritesViewModel(aplication.appProvider.provideFavoriteMovieRepository())
      }
    }
  }
}