package com.agarcia.myfirstandroidapp.data.repository.Review

import com.agarcia.myfirstandroidapp.data.model.Review
import kotlinx.coroutines.flow.Flow

interface ReviewRepository {
    fun getReviewsByMovieId(movieId: Int): Flow<List<Review>>
    fun getReviews(): Flow<List<Review>>
    suspend fun addReview(review: Review)
    suspend fun deleteReview(review: Review)
    suspend fun updateReview(review: Review)
}