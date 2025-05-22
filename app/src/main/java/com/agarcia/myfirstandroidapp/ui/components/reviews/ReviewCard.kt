package com.agarcia.myfirstandroidapp.ui.components.reviews

import androidx.compose.runtime.Composable
import com.agarcia.myfirstandroidapp.data.model.Review
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarHalf
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil3.compose.AsyncImage
import com.agarcia.myfirstandroidapp.helpers.formatLongDate
import com.agarcia.myfirstandroidapp.ui.screens.MovieDetail.MovieDetailViewModel

@Composable
fun ReviewCard(
    review: Review,
    onEdit: () -> Unit,
    onDelete: () -> Unit,
    modifier: Modifier = Modifier,
    includeMovieDetails: Boolean = false,
    movieDetailViewModel: MovieDetailViewModel = viewModel(),
) {
    val movie = movieDetailViewModel.getMovieById(review.movieId)

    Card (
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
    ) {
        if (includeMovieDetails && movie != null) {

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
                }
            }
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            // Header with author and date
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = review.author,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )

                Row {
                    IconButton(
                        onClick = onEdit
                    ) {
                        Icon(imageVector = Icons.Default.Edit, contentDescription = "Edit Review")
                    }

                    IconButton(
                        onClick = onDelete
                    ) {
                        Icon(imageVector = Icons.Default.Delete, contentDescription = "Delete Review")
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Rating with stars
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                repeat(5) { index ->
                    Icon(
                        imageVector = if (index < review.rating.toInt()) {
                            Icons.Filled.Star
                        } else if (index < review.rating && review.rating % 1 != 0.0) {
                            Icons.Filled.StarHalf
                        } else {
                            Icons.Outlined.Star
                        },
                        contentDescription = null,
                        tint = Color(0xFFFFD700), // Gold color
                        modifier = Modifier.size(16.dp)
                    )
                }

                Spacer(modifier = Modifier.width(8.dp))

                Text(
                    text = "${review.rating}/5.0",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Review description
            Text(
                text = review.description,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface,
                lineHeight = 20.sp
            )
        }
    }
}

// Preview function to see how it looks
@Preview(showBackground = true)
@Composable
fun ReviewCardPreview() {
    MaterialTheme {
        ReviewCard(
            review = Review(
                id = 1,
                author = "John Doe",
                rating = 4.5,
                description = "This movie was absolutely fantastic! The storyline was engaging, the acting was superb, and the cinematography was breathtaking. I would definitely recommend this to anyone looking for a great entertainment experience.",
                movieId = 123
            ),
            onEdit = {},
            onDelete = {}
        )
    }
}