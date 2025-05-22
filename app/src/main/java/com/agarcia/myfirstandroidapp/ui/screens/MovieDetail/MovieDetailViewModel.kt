package com.agarcia.myfirstandroidapp.ui.screens.MovieDetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.agarcia.myfirstandroidapp.data.dummy.dummyMovies
import com.agarcia.myfirstandroidapp.data.model.Movie
import com.agarcia.myfirstandroidapp.data.repository.FavoriteMovie.FavoriteMovieRepository
import com.agarcia.myfirstandroidapp.data.repository.FavoriteMovie.FavoriteMovieRepositoryImpl
import com.agarcia.myfirstandroidapp.data.repository.Movie.MovieRepository
import com.agarcia.myfirstandroidapp.data.repository.Movie.MovieRepositoryImpl
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class MovieDetailViewModel: ViewModel() {
  private val _movies = MutableStateFlow<List<Movie>>(dummyMovies)

  fun getMovieById(id: Int): Movie? {
    return _movies.value.firstOrNull { it.id == id }
  }
}