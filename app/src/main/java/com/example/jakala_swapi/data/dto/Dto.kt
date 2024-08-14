package com.example.jakala_swapi.data.dto

import com.example.jakala_swapi.data.db.entity.MovieDetailEntity
import com.example.jakala_swapi.data.model.MovieDetail

fun MovieDetail.toMovieDetailEntity(): MovieDetailEntity {
    return MovieDetailEntity(
        title = title ?: "No Title",
        episodeId = episodeId,
        openingCrawl = openingCrawl,
        releaseDate = releaseDate,
        isFavorite = isFavorite
    )
}

fun MovieDetailEntity.toMovieDetail(): MovieDetail {
    return MovieDetail(
        title = title,
        episodeId = episodeId,
        openingCrawl = openingCrawl,
        releaseDate = releaseDate,
        isFavorite = isFavorite
    )
}