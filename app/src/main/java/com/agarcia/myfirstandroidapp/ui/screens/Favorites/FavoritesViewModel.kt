package com.agarcia.myfirstandroidapp.ui.screens.Favorites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.agarcia.myfirstandroidapp.MyFirstAndroidAppAplication
import com.agarcia.myfirstandroidapp.data.model.FavoriteMovie
import com.agarcia.myfirstandroidapp.data.model.Movie
import com.agarcia.myfirstandroidapp.data.repository.FavoriteMovie.FavoriteMovieRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class FavoritesViewModel(
    private val favoritesRepository: FavoriteMovieRepository,
) : ViewModel() {

    private val _favoriteMovies = MutableStateFlow<List<FavoriteMovie>>(emptyList())
    val favoriteMovies: StateFlow<List<FavoriteMovie>> = _favoriteMovies

    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading

    val _isLinearLayout = MutableStateFlow<Boolean>(false)
    val isLinearLayout: StateFlow<Boolean> = _isLinearLayout

    fun changeLayoutPreferences() {
        viewModelScope.launch {
            _isLinearLayout.value != _isLinearLayout.value
        }
    }

    init {
        getFavoriteMovies()
    }

    private fun getFavoriteMovies() {
        viewModelScope.launch {
            _loading.value = true
            favoritesRepository.getFavoritesMovies().collect { movies ->
                _favoriteMovies.value = movies
                _loading.value = false
            }
        }
    }

    fun getMovieById(id: Int): FavoriteMovie? {
        return try {
            _favoriteMovies.value.first { it.movieId == id }
        } catch (e: NoSuchElementException) {
            null
        }
    }

    fun isFavoriteMovie(movieId: Int): Boolean {
        return try {
            _favoriteMovies.value.any { it.movieId == movieId }
        } catch (e: NoSuchElementException) {
            false
        }
    }

    fun toggleFavoriteMovie(movie: Movie) {
        viewModelScope.launch {
            if (isFavoriteMovie(movie.id)) {
                val favoriteMovie = getMovieById(movie.id)

                removeMovieFromFavorites(favoriteMovie!!)
            } else {
                val favoriteMovie = FavoriteMovie(id = movie.id, movieId = movie.id, title = movie.title, posterUrl = movie.posterUrl)

                addMovieToFavorites(favoriteMovie)
            }
        }
    }

    fun addMovieToFavorites(movie: FavoriteMovie) {
        viewModelScope.launch {
            favoritesRepository.addMovieToFavorites(movie)
        }
    }

    fun removeMovieFromFavorites(movie: FavoriteMovie) {
        viewModelScope.launch {
            favoritesRepository.removeMovieFromFavorites(movie)
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val aplication = this[APPLICATION_KEY] as MyFirstAndroidAppAplication
                FavoritesViewModel(aplication.appProvider.provideFavoriteMovieRepository())
            }
        }
    }
}