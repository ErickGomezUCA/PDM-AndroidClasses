package com.agarcia.myfirstandroidapp.ui.screens.MovieList

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.agarcia.myfirstandroidapp.MyFirstAndroidAppAplication
import com.agarcia.myfirstandroidapp.data.dummy.dummyMovies
import com.agarcia.myfirstandroidapp.data.model.Movie
import com.agarcia.myfirstandroidapp.data.model.toFavoriteMovie
import com.agarcia.myfirstandroidapp.data.repository.FavoriteMovie.FavoriteMovieRepository
import com.agarcia.myfirstandroidapp.data.repository.Movie.MovieRepository
import com.agarcia.myfirstandroidapp.data.repository.Movie.MovieRepositoryImpl
import com.agarcia.myfirstandroidapp.data.repository.Settings.UserPreferencesRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class MovieListViewModel(
  private val userPreferenceRepository: UserPreferencesRepository,
  private val favoriteMovieRepository: FavoriteMovieRepository
): ViewModel() {
  val movieRepository:MovieRepository = MovieRepositoryImpl()

  private val _movies = MutableStateFlow<List<Movie>>(emptyList())
  val movies: StateFlow<List<Movie>> = _movies

  private val _loading = MutableStateFlow<Boolean>(false)
  val loading : StateFlow<Boolean> = _loading

  val isLinearLayout : StateFlow<Boolean> = userPreferenceRepository.isLinearLayout.map { it }
    .stateIn(
      scope = viewModelScope,
      started = SharingStarted.WhileSubscribed(5_000),
      initialValue = true
    )

  fun changeLayoutPreferences(isLinearLayout: Boolean) {
    viewModelScope.launch {
      userPreferenceRepository.saveLayoutPreference(isLinearLayout)
    }
  }

  fun loadMovies () {
    viewModelScope.launch {
      _loading.value = true
      _movies.value = movieRepository.getMovies()
      _loading.value = false
    }
  }

  fun isFavoriteMovie(movieId: Int): StateFlow<Boolean> {
    return favoriteMovieRepository.isFavorite(movieId)
      .stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = false
      )
  }

  fun toggleFavoriteMovie(movie: Movie) {
    viewModelScope.launch {
      val isFavorite = favoriteMovieRepository.isFavorite(movie.id).first()
      
      if (isFavorite) {
        favoriteMovieRepository.removeMovieFromFavorites(movie.toFavoriteMovie())
      } else {
        favoriteMovieRepository.addMovieToFavorites(movie.toFavoriteMovie())
      }
    }
  }

  companion object {
    val Factory: ViewModelProvider.Factory = viewModelFactory {
      initializer {
        val aplication = this[APPLICATION_KEY] as MyFirstAndroidAppAplication
        MovieListViewModel(
          aplication.appProvider.provideUserPreferenceRepository(),
          aplication.appProvider.provideFavoriteMovieRepository()
        )
      }
    }
  }

}