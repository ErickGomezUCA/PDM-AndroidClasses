package com.agarcia.myfirstandroidapp.ui.layout

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Tv
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.agarcia.myfirstandroidapp.ui.navigations.MainNavigation
import com.agarcia.myfirstandroidapp.ui.navigations.MovieListScreenNavigation
import com.agarcia.myfirstandroidapp.ui.navigations.MyFavoritesScreenNavigation
import com.agarcia.myfirstandroidapp.ui.navigations.ReviewsScreenNavigation
import kotlinx.coroutines.launch

const val MovieListScreenNavigationId = "com.agarcia.myfirstandroidapp.ui.navigations.MovieDetailScreenNavigation/{id}"
data class NavItem(val label: String, val icon: ImageVector, val route: String)

@Composable
fun CustomScaffold () {
  val navController = rememberNavController()
  val coroutineScope = rememberCoroutineScope()
  val snackbarHostState = remember { SnackbarHostState() }

  val currentDestination = navController
    .currentBackStackEntryAsState().value?.destination?.route

  var title by rememberSaveable { mutableStateOf("Tendencias") }
  var selectedItem by rememberSaveable { mutableStateOf("nowplaying") }

  val navItems = listOf(
    NavItem("Tendencias", Icons.Filled.Home, "nowplaying"),
    NavItem("Favorites", Icons.Filled.Favorite, "favorites"),
    NavItem("Up comming", Icons.Filled.Tv, "upcomming")
  )

  fun onFloatingButtonClick(text:String) {
    coroutineScope.launch {
      snackbarHostState.showSnackbar(
        message = text,
        actionLabel = "OK",
        duration = SnackbarDuration.Short,
      )
    }
  }

  fun onItemSelected(currentItem: String) {
    selectedItem = currentItem
    title = when (currentItem) {
      "nowplaying" -> "Tendencias"
      "favorites" -> "Mis Favoritas"
      "upcomming" -> "Próximamente"
      else -> "Inicio"
    }
    when (currentItem) {
      "nowplaying" -> navController.navigate(MovieListScreenNavigation)
      "favorites" -> navController.navigate(MyFavoritesScreenNavigation)
      "upcomming" -> navController.navigate(ReviewsScreenNavigation)
      else -> navController.navigate(MovieListScreenNavigation)
    }
  }

  Scaffold(
    topBar = { CustomTopBar(
      title = title,
      showBackButton = currentDestination == MovieListScreenNavigationId,
      onBackClick = { navController.popBackStack() },
    )},
    bottomBar = { CustomBottomBar(
      navItems = navItems,
      selectedItem = selectedItem,
      onItemSelected = { onItemSelected(it) }
    )},
    snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
    floatingActionButton = { CustomFloatingButton( onClick = { onFloatingButtonClick("Hola Mundo desde el Floating Button")}) },
    ) { innerPadding ->
    Column (
      modifier = Modifier.padding(innerPadding).fillMaxSize(),
      verticalArrangement = Arrangement.Center,
      horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally
    ) {
      MainNavigation(navController = navController)
    }
  }
}

@Preview(showBackground = true)
@Composable
fun CustomScaffoldPreview() {
  CustomScaffold()
}