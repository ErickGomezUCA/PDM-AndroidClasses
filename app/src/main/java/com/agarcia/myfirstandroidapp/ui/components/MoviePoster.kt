package com.agarcia.myfirstandroidapp.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.agarcia.myfirstandroidapp.data.model.Movie

@Composable
fun MoviePoster(
  movie: Movie,
  isFavorite: Boolean,
  onMovieClick: (Int) -> Unit,
  onFavoriteClick: () -> Unit = {},
) {
  Box(
    modifier = Modifier.height(175.dp).clip(RoundedCornerShape(12.dp)),
  ) {
    AsyncImage(
      model=movie.posterUrl,
      contentDescription = movie.title,
      modifier = Modifier.matchParentSize().clickable { onMovieClick(movie.id) },
    )

    IconButton(
      onClick = {
        onFavoriteClick()
      },
      modifier = Modifier
        .align(Alignment.TopEnd)
        .height(40.dp)
        .width(40.dp)
    ) {
      Icon(
        imageVector = Icons.Filled.Favorite,
        contentDescription = "Favorite",
        tint = if (isFavorite) Color.Red else Color.White,
        modifier = Modifier.matchParentSize()
      )
    }


  }


}