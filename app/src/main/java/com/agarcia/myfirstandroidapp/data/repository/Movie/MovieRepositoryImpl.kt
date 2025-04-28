package com.agarcia.myfirstandroidapp.data.repository.Movie

import com.agarcia.myfirstandroidapp.data.dummy.dummyMovies
import com.agarcia.myfirstandroidapp.data.model.Movie
import kotlinx.coroutines.delay

class MovieRepositoryImpl:MovieRepository {

  override suspend fun getMovies(): List<Movie> {
    delay(5000)
    return dummyMovies
  }
}