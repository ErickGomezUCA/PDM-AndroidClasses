package com.agarcia.myfirstandroidapp.data.database

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import android.content.Context
import com.agarcia.myfirstandroidapp.data.database.dao.FavoriteMovieDao
import com.agarcia.myfirstandroidapp.data.database.entities.FavoriteMovieEntity

@Database(entities = [FavoriteMovieEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase: RoomDatabase() {
    abstract fun favoriteMovieDao(): FavoriteMovieDao

    @Volatile
    private var INSTANCE: AppDatabase? = null

    fun getDatabase(context: Context): AppDatabase {
        return INSTANCE ?: synchronized(this) {
            val INSTANCE = Room.databaseBuilder(
                context = context.applicationContext,
                klass = AppDatabase::class.java,
                name = "app_database"
            ).fallbackToDestructiveMigration(false)
                .build()
                .also { INSTANCE = it }
            INSTANCE
        }

    }
}