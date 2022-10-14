package com.imtuc.week4retrofit.repository

import com.imtuc.week4retrofit.retrofit.EndPointAPI
import javax.inject.Inject

class MoviesRepository @Inject constructor(
    private val api: EndPointAPI
) {

    suspend fun getNowPlayingData(
        apiKey: String,
        language: String,
        page: Int)
    = api.getNowPlaying(apiKey, language, page)

    suspend fun getMovieDetails(
        movie_id: Int,
        apiKey: String)
            = api.getMovieDetails(movie_id, apiKey)

}