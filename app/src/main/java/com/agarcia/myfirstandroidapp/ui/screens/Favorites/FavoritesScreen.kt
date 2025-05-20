package com.agarcia.myfirstandroidapp.ui.screens.Favorites

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.GridView
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.agarcia.myfirstandroidapp.data.model.FavoriteMovie
import com.agarcia.myfirstandroidapp.data.model.Movie
import com.agarcia.myfirstandroidapp.ui.components.FavoriteMovieItem
import com.agarcia.myfirstandroidapp.ui.components.FavoriteMoviePoster
import com.agarcia.myfirstandroidapp.ui.components.MovieItem
import com.agarcia.myfirstandroidapp.ui.components.MoviePoster
import com.agarcia.myfirstandroidapp.ui.screens.MovieList.MovieListScreen

@Composable
fun Favorites(
  onMovieClick : (Int) -> Unit = {},
  viewModel: FavoritesViewModel = viewModel(factory = FavoritesViewModel.Factory)
) {
  val favoriteMovies by viewModel.favoriteMovies.collectAsState()
  val loading by viewModel.loading.collectAsState()
  val isLinearLayout by viewModel.isLinearLayout.collectAsState()

  if (loading) {
    Box(
      modifier = Modifier.fillMaxSize(),
      contentAlignment = Alignment.Center
    ) {
      CircularProgressIndicator()
    }

    return
  }

  Column (
    modifier = Modifier
      .fillMaxSize()
      .padding(16.dp, 0.dp, 16.dp, 16.dp)
  ) {
    Row() {
      Text(
        text = "Listado de peliculas",
        modifier = Modifier
          .align(Alignment.CenterVertically)
      )
      Spacer(modifier = Modifier.weight(1f))
      MovieListLayoutSwitch(
        isLinearLayout = isLinearLayout,
        onClick = { viewModel.changeLayoutPreferences() }
      )
    }

    if (isLinearLayout) {
      MoviesLinearLayout(favoriteMovies, onMovieClick)
    } else {
      MoviesGridLayout(favoriteMovies, onMovieClick)
    }
  }
}

@Composable
fun MoviesLinearLayout (movies: List<FavoriteMovie>, onMovieClick: (Int) -> Unit) {
  LazyColumn() {
    items(movies) { movie ->
      FavoriteMovieItem(movie = movie, onMovieClick = onMovieClick)
      Spacer(modifier = Modifier.height(16.dp))
    }
  }
}

@Composable
fun MoviesGridLayout (movies: List<FavoriteMovie>, onMovieClick: (Int) -> Unit) {
  LazyVerticalGrid(
    columns = GridCells.Fixed(3),
    contentPadding = PaddingValues(0.dp),
    verticalArrangement = Arrangement.spacedBy(8.dp),
    horizontalArrangement = Arrangement.spacedBy(8.dp)
  ) {
    items(movies) { movie ->
      FavoriteMoviePoster(movie = movie, onMovieClick = onMovieClick)
    }
  }
}

@Composable
fun MovieListLayoutSwitch(isLinearLayout: Boolean, onClick: () -> Unit) {
  IconButton(
    onClick = onClick,
  ) {
    Icon(
      imageVector = if (isLinearLayout) {
        Icons.AutoMirrored.Filled.List
      } else {
        Icons.Default.GridView
      },
      contentDescription = "Change Layout"
    )
  }
}

@Preview(showBackground = true)
@Composable
fun MovieListScreenPreview(){
  MovieListScreen()
}