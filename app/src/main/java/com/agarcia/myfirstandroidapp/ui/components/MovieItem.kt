package com.agarcia.myfirstandroidapp.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.agarcia.myfirstandroidapp.data.model.Movie
import com.agarcia.myfirstandroidapp.helpers.formatLongDate

@Composable
fun MovieItem(movie: Movie, onMovieClick: (Int) -> Unit) {
  Card(
    shape = RoundedCornerShape(12.dp),
    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
    modifier = Modifier.fillMaxWidth().clickable { onMovieClick(movie.id) }
  ) {
    Row(modifier = Modifier.padding(16.dp)) {
      AsyncImage(
        model=movie.posterUrl,
        contentDescription = movie.title,
        modifier = Modifier.height(120.dp).width(80.dp).clip(RoundedCornerShape(8.dp)),
      )

      Spacer(modifier = Modifier.width(16.dp))

      Column(modifier = Modifier.weight(1f)) {
        Text(
          text = movie.title,
          style = MaterialTheme.typography.titleMedium,
          maxLines = 2,
          overflow = TextOverflow.Ellipsis
        )
        Text(
          text = "Estreno: ${formatLongDate(movie.releaseDate)}",
          style = MaterialTheme.typography.bodySmall,
          color = Color.Gray
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
          text = movie.overview,
          style = MaterialTheme.typography.bodyMedium,
          maxLines = 3,
          overflow = TextOverflow.Ellipsis
        )
      }
    }
  }
}