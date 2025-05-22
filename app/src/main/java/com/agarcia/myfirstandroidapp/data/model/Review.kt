package com.agarcia.myfirstandroidapp.data.model

import com.agarcia.myfirstandroidapp.data.database.entities.ReviewEntity

data class Review (
    val id: Int,
    val author: String,
    val rating: Double,
    val description: String,
    val movieId: Int
)

fun Review.toDatabase(): ReviewEntity {
    return ReviewEntity(
        id = id,
        author = author,
        rating = rating,
        description = description,
        movieId = movieId
    )
}