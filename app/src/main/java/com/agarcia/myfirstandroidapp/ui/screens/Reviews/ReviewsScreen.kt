package com.agarcia.myfirstandroidapp.ui.screens.Reviews

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.dp
import com.agarcia.myfirstandroidapp.data.model.Review
import com.agarcia.myfirstandroidapp.ui.components.reviews.ReviewCard
import com.agarcia.myfirstandroidapp.ui.components.reviews.ReviewDialog

@Composable
fun ReviewsScreen(
    viewModel: ReviewViewModel = viewModel(factory = ReviewViewModel.Factory)
) {
    val reviews by viewModel.reviews.collectAsState()
    val loading by viewModel.loading.collectAsState()

    // Dialog state
    var showReviewDialog by remember { mutableStateOf(false) }
    var reviewToEdit by remember { mutableStateOf<Review?>(null) }

    if (loading) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
        return
    }

    if (reviews.isEmpty()) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "No hay reseñas disponibles",
                style = MaterialTheme.typography.titleLarge
            )
        }
        return
    }

    Column(modifier = Modifier
      .fillMaxSize()
      .padding(16.dp, 0.dp, 16.dp, 16.dp)) {
        Row {
            Text(
                text = "Reseñas",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(bottom = 16.dp)
            )
        }

        LazyColumn {
            items(reviews) { review ->
                ReviewCard(
                    review = review,
                    onEdit = {
                        reviewToEdit = review
                        showReviewDialog = true
                    },
                    onDelete = {
                        viewModel.deleteReview(review)
                    },
                    includeMovieDetails = true
                )
            }
        }
    }

    // Show dialog when state is true
    if (showReviewDialog) {
        ReviewDialog(
            movieId = reviewToEdit?.movieId!!,
            existingReview = reviewToEdit,
            onDismiss = {
                showReviewDialog = false
                reviewToEdit = null
            },
            onSubmit = { review ->
                if (reviewToEdit != null) {
                    // Update existing review
                    viewModel.updateReview(review)
                } else {
                    // Add new review
                    viewModel.addReview(review)
                }
                showReviewDialog = false
                reviewToEdit = null
            }
        )
    }
}