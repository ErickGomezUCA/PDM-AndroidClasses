package com.agarcia.myfirstandroidapp

import android.app.Application
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.agarcia.myfirstandroidapp.data.AppProvider
import com.agarcia.myfirstandroidapp.data.repository.Settings.UserPreferenceRepository

private const val USER_PREFERENCE_NAME = "user_preferences"
private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = USER_PREFERENCE_NAME)

class MyFirstAndroidAppAplication:Application() {
    val appProvider by lazy {
        AppProvider(this)
    }

    lateinit var userPreferenceRepository: UserPreferenceRepository

    override fun onCreate() {
        super.onCreate()
        userPreferenceRepository = UserPreferenceRepository(dataStore)

        // val userPreferenceRepository = appProvider.provideUserPreferenceRepository()
        val favoriteMovieRepository = appProvider.provideFavoriteMovieRepository()
        val repositoryRepository = appProvider.provideReviewRepository()
    }
}