package com.agarcia.myfirstandroidapp.ui.screens.Reviews

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.agarcia.myfirstandroidapp.MyFirstAndroidAppAplication
import com.agarcia.myfirstandroidapp.data.model.Review
import com.agarcia.myfirstandroidapp.data.repository.Review.ReviewRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class ReviewViewModel(
    private val reviewRepository: ReviewRepository,
) : ViewModel() {
    private val _reviews = MutableStateFlow<List<Review>>(emptyList())
    val reviews = _reviews

    private val _loading = MutableStateFlow(false)
    val loading = _loading

    init {
        getReviews()
    }

    private fun getReviews() {
        viewModelScope.launch {
            _loading.value = true
            reviewRepository.getReviews().collect { reviews ->
                _reviews.value = reviews
                _loading.value = false
            }
        }
    }

    private fun getReviewsByMovieId(movieId: Int) {
        viewModelScope.launch {
            _loading.value = true
            reviewRepository.getReviewsByMovieId(movieId = movieId).collect { reviews ->
                _reviews.value = reviews
                _loading.value = false
            }
        }
    }

    fun addReview(review: Review) {
        viewModelScope.launch {
            reviewRepository.addReview(review)
        }
    }

    fun deleteReview(review: Review) {
        viewModelScope.launch {
            reviewRepository.deleteReview(review)
        }
    }

    fun updateReview(review: Review) {
        viewModelScope.launch {
            reviewRepository.updateReview(review)
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = this[APPLICATION_KEY] as MyFirstAndroidAppAplication
                ReviewViewModel(application.appProvider.provideReviewRepository())
            }
        }
    }
}