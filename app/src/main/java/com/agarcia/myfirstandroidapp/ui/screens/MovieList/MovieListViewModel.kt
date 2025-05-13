package com.agarcia.myfirstandroidapp.ui.screens.MovieList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.agarcia.myfirstandroidapp.MyFirstAndroidAppAplication
import com.agarcia.myfirstandroidapp.data.dummy.dummyMovies
import com.agarcia.myfirstandroidapp.data.model.Movie
import com.agarcia.myfirstandroidapp.data.repository.Movie.MovieRepository
import com.agarcia.myfirstandroidapp.data.repository.Movie.MovieRepositoryImpl
import com.agarcia.myfirstandroidapp.data.repository.Settings.UserPreferenceRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class MovieListViewModel(
  private val userPreferenceRepository: UserPreferenceRepository
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
      userPreferenceRepository.saveUserPreferences(isLinearLayout)
    }
  }

  fun loadMovies () {
    viewModelScope.launch {
      _loading.value = true
      _movies.value = movieRepository.getMovies()
      _loading.value = false
    }
  }

  companion object {
    val Factory: ViewModelProvider.Factory = viewModelFactory {
      initializer {
        val userPreferenceRepository = (this[APPLICATION_KEY] as MyFirstAndroidAppAplication).userPreferenceRepository
        MovieListViewModel(userPreferenceRepository)
      }
    }
  }

}