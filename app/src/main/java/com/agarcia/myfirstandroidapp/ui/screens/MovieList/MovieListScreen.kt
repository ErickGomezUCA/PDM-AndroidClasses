package com.agarcia.myfirstandroidapp.ui.screens.MovieList

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.agarcia.myfirstandroidapp.ui.components.MovieItem

@Composable
fun MovieListScreen(
  onMovieClick : (Int) -> Unit = {},
  viewModel: MovieListViewModel = viewModel()
){
  val movies by viewModel.movies.collectAsState()

  val loading by viewModel.loading.collectAsState()

  LaunchedEffect(Unit) {
    viewModel.loadMovies()
  }

  if(loading){
    CircularProgressIndicator()
    return
  }

  LazyColumn(
    modifier = Modifier
      .fillMaxSize()
      .padding(16.dp)
  ) {
    items(movies) { movie ->
      MovieItem(movie = movie, onMovieClick = onMovieClick)
      Spacer(modifier = Modifier.height(16.dp))
    }
  }
}

@Preview(showBackground = true)
@Composable
fun MovieListScreenPreview(){
  MovieListScreen()
}