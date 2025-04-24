package com.agarcia.myfirstandroidapp.ui.navigations

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.agarcia.myfirstandroidapp.ui.screens.Favorites.Favorites
import com.agarcia.myfirstandroidapp.ui.screens.MovieDetailScreen
import com.agarcia.myfirstandroidapp.ui.screens.MovieListScreen
import com.agarcia.myfirstandroidapp.ui.screens.UpComming.UpComming

@Composable
fun MainNavigation(navController: NavHostController) {
  val onMovieClick = { movieId: Int ->
    navController.navigate(MovieDetailScreenNavigation(movieId))
  }

  NavHost(navController = navController, startDestination = MovieListScreenNavigation) {
    composable <MovieListScreenNavigation> {
      MovieListScreen(onMovieClick)
    }
    composable <MovieDetailScreenNavigation> { backStackEntry ->
      val movieId = backStackEntry.arguments?.getInt("id") ?: 0
      MovieDetailScreen(movieId = movieId)
    }
    composable<MyFavoritesScreenNavigation> {
      Favorites()
    }
    composable<UpCommingScreenNavigation> {
      UpComming()
    }
  }
}