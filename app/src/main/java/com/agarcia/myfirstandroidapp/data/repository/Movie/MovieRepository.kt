package com.agarcia.myfirstandroidapp.data.repository.Movie

import com.agarcia.myfirstandroidapp.data.model.Movie

interface MovieRepository {
  suspend fun getMovies(): List<Movie>
}