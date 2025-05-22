package com.agarcia.myfirstandroidapp.data

import android.content.Context
import com.agarcia.myfirstandroidapp.data.database.AppDatabase
import com.agarcia.myfirstandroidapp.data.repository.FavoriteMovie.FavoriteMovieRepository
import com.agarcia.myfirstandroidapp.data.repository.FavoriteMovie.FavoriteMovieRepositoryImpl
import com.agarcia.myfirstandroidapp.data.repository.Review.ReviewRepository
import com.agarcia.myfirstandroidapp.data.repository.Review.ReviewRepositoryImpl
import com.agarcia.myfirstandroidapp.data.repository.Settings.UserPreferenceRepository

class AppProvider(context: Context) {
  private val appDatabase = AppDatabase.getDatabase(context)
  private val favoriteMovieDao = appDatabase.favoriteMovieDao()
  private val favoriteMovieRepository: FavoriteMovieRepository = FavoriteMovieRepositoryImpl(favoriteMovieDao)
  private val reviewDao = appDatabase.reviewDao()
  private val reviewRepository: ReviewRepository = ReviewRepositoryImpl(reviewDao)

  fun provideFavoriteMovieRepository() : FavoriteMovieRepository {
    return favoriteMovieRepository
  }

  fun provideReviewRepository() : ReviewRepository {
    return reviewRepository
  }

//  fun provideUserPreferenceRepository() : UserPreferenceRepository {
//  }
}