package com.agarcia.myfirstandroidapp.ui.screens.Favorites

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.agarcia.myfirstandroidapp.ui.components.MoviePoster
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.agarcia.myfirstandroidapp.data.model.toMovie


@Composable
fun Favorites(
  favoritesViewModel: FavoritesViewModel = viewModel(factory = FavoritesViewModel.Factory)
) {
  val favoriteMovies by favoritesViewModel.favoriteMovies.collectAsState(emptyList())

  LazyVerticalGrid(
    columns = GridCells.Fixed(3),
    contentPadding = PaddingValues(0.dp),
    verticalArrangement = Arrangement.spacedBy(8.dp),
    horizontalArrangement = Arrangement.spacedBy(8.dp),
    modifier = Modifier.fillMaxSize().padding(16.dp)
  ) {
    items(favoriteMovies) { movie ->
      val isFavorite by favoritesViewModel.isFavorite(movie.id).collectAsState(false)
      MoviePoster(
        movie = movie.toMovie(),
        isFavorite = isFavorite,
        onMovieClick = {},
        onFavoriteClick = { favoritesViewModel.toggleFavorite(movie) }

      )
    }
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