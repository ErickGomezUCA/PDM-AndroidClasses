package com.agarcia.myfirstandroidapp.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.agarcia.myfirstandroidapp.data.model.FavoriteMovie
import com.agarcia.myfirstandroidapp.data.model.Movie

@Composable
fun FavoriteMoviePoster(movie: FavoriteMovie, onMovieClick: (Int) -> Unit) {
    AsyncImage(
        model=movie.posterUrl,
        contentDescription = movie.title,
        modifier = Modifier.height(175.dp).clip(RoundedCornerShape(12.dp)).clickable { onMovieClick(movie.id) },
    )
}