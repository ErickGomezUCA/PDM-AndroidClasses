package com.agarcia.myfirstandroidapp.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.agarcia.myfirstandroidapp.data.model.Review

@Entity(tableName = "Review")
data class ReviewEntity (
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val author: String,
    val rating: Double,
    val description: String,
    val movieId: Int
)

fun ReviewEntity.toDomain(): Review {
    return Review(
        id = id,
        author = author,
        rating = rating,
        description = description,
        movieId = movieId
    )
}