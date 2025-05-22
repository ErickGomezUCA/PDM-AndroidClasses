package com.agarcia.myfirstandroidapp.ui.screens.MovieDetail

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil3.compose.AsyncImage
import com.agarcia.myfirstandroidapp.data.model.Review
import com.agarcia.myfirstandroidapp.helpers.formatLongDate
import com.agarcia.myfirstandroidapp.ui.components.reviews.ReviewCard
import com.agarcia.myfirstandroidapp.ui.components.reviews.ReviewDialog
import com.agarcia.myfirstandroidapp.ui.screens.Favorites.FavoritesViewModel
import com.agarcia.myfirstandroidapp.ui.screens.Reviews.ReviewViewModel

@Composable
fun MovieDetailScreen(
    movieId: Int,
    viewModel: MovieDetailViewModel = viewModel(),
    favoritesViewModel: FavoritesViewModel = viewModel(factory = FavoritesViewModel.Factory),
    reviewViewModel: ReviewViewModel = viewModel(factory = ReviewViewModel.Factory)
) {
    val scrollState = rememberScrollState()
    val movie = viewModel.getMovieById(movieId)
    val favoriteMovies = favoritesViewModel.favoriteMovies.collectAsState().value
    val isFavorite = favoriteMovies.any { it.movieId == movie.id }

    // Dialog state
    var showReviewDialog by remember { mutableStateOf(false) }
    var reviewToEdit by remember { mutableStateOf<Review?>(null) }

    if (movie == null) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text("Película no encontrada", style = MaterialTheme.typography.titleLarge)
        }
        return
    }

    Column(
        modifier = Modifier
          .fillMaxSize()
          .verticalScroll(scrollState)
          .background(Color.White)
    ) {
        AsyncImage(
            model = movie.backdropUrl,
            contentDescription = null,
            modifier = Modifier
              .fillMaxWidth()
              .height(200.dp),
            contentScale = ContentScale.Crop
        )

        // Poster y Título
        Row(
            modifier = Modifier
                .padding(16.dp)
        ) {
            AsyncImage(
                model = movie.posterUrl,
                contentDescription = null,
                modifier = Modifier
                  .width(100.dp)
                  .height(150.dp)
                  .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column {
                Button(onClick = {
                    favoritesViewModel.toggleFavoriteMovie(movie = movie)
                    Log.d("fav", favoritesViewModel.isFavoriteMovie(movie.id).toString());
                }) {
                    Icon(
                        imageVector = if (isFavorite) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                        contentDescription = "Agregar a Favoritos"
                    )
                    Text(text = if (isFavorite) "Eliminar de Favoritos" else "Agregar a Favoritos")
                }
                Text(
                    text = movie.title,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "(${movie.originalTitle})",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Gray
                )
                Text(
                    text = "Idioma original: ${movie.originalLanguage.uppercase()}",
                    style = MaterialTheme.typography.bodySmall
                )
                Text(
                    text = "Estreno: ${formatLongDate(movie.releaseDate)}",
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }

        HorizontalDivider()

        // Descripción
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "Sinopsis",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = movie.overview)
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier
              .fillMaxWidth()
              .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = "Popularidad: ${movie.popularity}")
            Text(text = "⭐ ${movie.voteAverage} (${movie.voteCount} votos)")
        }

        Spacer(modifier = Modifier.height(16.dp))

        HorizontalDivider()

//    Reviews
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "Reseñas",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold
            )
            Spacer(modifier = Modifier.height(8.dp))

            val reviews = reviewViewModel.reviews.collectAsState().value

            Row(modifier = Modifier.fillMaxWidth()) {
                Button(
                    onClick = {
                        reviewToEdit = null // Clear any existing review
                        showReviewDialog = true
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = "Agregar reseña")
                }
            }

            if (reviews.isEmpty()) {
                Text(text = "No hay reseñas disponibles")
            } else {
                reviews.forEach { review ->
                    ReviewCard(
                        review = review,
                        onEdit = {
                            reviewToEdit = review
                            showReviewDialog = true
                        },
                        onDelete = {
                            reviewViewModel.deleteReview(review)
                        }
                    )
                }
            }
        }
    }

    // Show dialog when state is true
    if (showReviewDialog) {
        ReviewDialog(
            movieId = movieId,
            existingReview = reviewToEdit,
            onDismiss = {
                showReviewDialog = false
                reviewToEdit = null
            },
            onSubmit = { review ->
                if (reviewToEdit != null) {
                    // Update existing review
                    reviewViewModel.updateReview(review)
                } else {
                    // Add new review
                    reviewViewModel.addReview(review)
                }
                showReviewDialog = false
                reviewToEdit = null
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun MovieDetailScreenPreview() {
    MovieDetailScreen(798418)
}