package com.agarcia.myfirstandroidapp.data.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.agarcia.myfirstandroidapp.data.database.entities.ReviewEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ReviewDao {
    @Query("SELECT * FROM Review WHERE movieId = :movieId")
    fun getReviewsByMovieId(movieId: Int): Flow<List<ReviewEntity>>

    @Query("SELECT * FROM Review")
    fun getAllReviews(): Flow<List<ReviewEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addReview(review: ReviewEntity)

    @Delete
    suspend fun deleteReview(review: ReviewEntity)

    @Update
    suspend fun updateReview(review: ReviewEntity)
}