package com.agarcia.myfirstandroidapp.data.repository.Settings

import kotlinx.coroutines.flow.Flow
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.emptyPreferences
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

class UserPreferencesRepositoryImpl (
  private val dataStore: DataStore<Preferences>
) : UserPreferencesRepository {
  private companion object {
    val IS_LINEAR_LAYOUT = booleanPreferencesKey("IS_LINEAR_LAYOUT")
  }

  override val isLinearLayout: Flow<Boolean> = dataStore.data
    .catch {
      if(it is IOException) {
        emit(emptyPreferences())
      } else {
        throw it
      }
    }
    .map{ preferences ->
      preferences[IS_LINEAR_LAYOUT] ?: true
    }

  override suspend fun saveLayoutPreference(isLinearLayout: Boolean) {
    dataStore.edit{ preferences ->
      preferences[IS_LINEAR_LAYOUT] = isLinearLayout
    }
  }
}