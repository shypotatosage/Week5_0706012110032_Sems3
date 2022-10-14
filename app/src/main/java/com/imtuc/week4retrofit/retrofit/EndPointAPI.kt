package com.imtuc.week4retrofit.retrofit

import com.imtuc.week4retrofit.model.MovieDetails
import com.imtuc.week4retrofit.model.NowPlaying
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface EndPointAPI {

    @GET("movie/now_playing")
    suspend fun getNowPlaying(
        @Query("api_key") apiKey: String,
        @Query("language") lang: String,
        @Query("page") page: Int
    ): Response<NowPlaying>

    @GET("movie/{movie_id}")
    suspend fun getMovieDetails(
        @Path("movie_id") id: Int,
        @Query("api_key") apiKey: String
    ): Response<MovieDetails>

}