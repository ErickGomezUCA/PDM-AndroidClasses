package com.agarcia.myfirstandroidapp.ui.screens.MovieList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.agarcia.myfirstandroidapp.data.dummy.dummyMovies
import com.agarcia.myfirstandroidapp.data.model.Movie
import com.agarcia.myfirstandroidapp.data.repository.Movie.MovieRepository
import com.agarcia.myfirstandroidapp.data.repository.Movie.MovieRepositoryImpl
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MovieListViewModel: ViewModel() {
  val movieRepository:MovieRepository = MovieRepositoryImpl()

  private val _movies = MutableStateFlow<List<Movie>>(emptyList())
  val movies: StateFlow<List<Movie>> = _movies

  private val _loading = MutableStateFlow<Boolean>(false)
  val loading : StateFlow<Boolean> = _loading

  fun loadMovies () {
    viewModelScope.launch {
      _loading.value = true
      _movies.value = movieRepository.getMovies()
      _loading.value = false
    }
  }

}