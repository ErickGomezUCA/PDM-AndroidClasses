package com.agarcia.myfirstandroidapp.data.repository.Review

import com.agarcia.myfirstandroidapp.data.database.dao.ReviewDao
import com.agarcia.myfirstandroidapp.data.database.entities.toDomain
import com.agarcia.myfirstandroidapp.data.model.Review
import com.agarcia.myfirstandroidapp.data.model.toDatabase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ReviewRepositoryImpl(
    private val reviewDao: ReviewDao
): ReviewRepository {
    override fun getReviewsByMovieId(movieId: Int): Flow<List<Review>> {
        return reviewDao.getReviewsByMovieId(movieId = movieId).map { list ->
            list.map {it.toDomain()}
        }
    }

    override fun getReviews(): Flow<List<Review>> {
        return reviewDao.getAllReviews().map { list ->
            list.map {it.toDomain()}
        }
    }

    override suspend fun addReview(review: Review) {
        reviewDao.addReview(review.toDatabase())
    }

    override suspend fun deleteReview(review: Review) {
        reviewDao.deleteReview(review.toDatabase())
    }

    override suspend fun updateReview(review: Review) {
        reviewDao.updateReview(review.toDatabase())
    }
}