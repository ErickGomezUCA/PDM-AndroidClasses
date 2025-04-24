package com.agarcia.myfirstandroidapp.ui.screens

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.agarcia.myfirstandroidapp.data.dummy.movies
import com.agarcia.myfirstandroidapp.ui.components.MovieItem

@Composable
fun MovieListScreen(
  onMovieClick : (Int) -> Unit = {}
){
  val movies = movies
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