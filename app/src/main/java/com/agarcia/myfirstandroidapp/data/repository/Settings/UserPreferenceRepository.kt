package com.agarcia.myfirstandroidapp.data.repository.Settings

import kotlinx.coroutines.flow.Flow

interface UserPreferencesRepository {

  val isLinearLayout: Flow<Boolean>

  suspend fun saveLayoutPreference(isGrid: Boolean)
}