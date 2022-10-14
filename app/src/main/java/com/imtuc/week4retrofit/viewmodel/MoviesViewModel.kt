package com.imtuc.week4retrofit.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.imtuc.week4retrofit.model.MovieDetails
import com.imtuc.week4retrofit.repository.MoviesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import com.imtuc.week4retrofit.model.Result
import kotlinx.coroutines.launch

@HiltViewModel
class MoviesViewModel @Inject constructor(
    private val repo: MoviesRepository
): ViewModel() {

    //Get Now Playing Data
    val _nowPlaying: MutableLiveData<ArrayList<Result>> by lazy {
        MutableLiveData<ArrayList<Result>>()
    }

    val nowPlaying: LiveData<ArrayList<Result>>
        get() = _nowPlaying

    fun getNowPlaying(apiKey: String, language: String, page: Int)
    = viewModelScope.launch {
        repo.getNowPlayingData(apiKey, language, page).let {
            response ->
            if (response.isSuccessful) {
                _nowPlaying.postValue(response.body()?.results as ArrayList<Result>?)
            } else {
                Log.e("Get Now Playing Datas", "Failed!")
            }
        }
    }

    // Get Movie Details
    val _movieDetails: MutableLiveData<MovieDetails> by lazy {
        MutableLiveData<MovieDetails>()
    }

    val movieDetails: LiveData<MovieDetails>
        get() = _movieDetails

    fun getMovieDetails(movie_id: Int, apiKey: String)
            = viewModelScope.launch {
        repo.getMovieDetails(movie_id, apiKey).let {
                response ->
            if (response.isSuccessful) {
                _movieDetails.postValue(response.body() as MovieDetails)
            } else {
                Log.e("Get Movie Details", "Failed!")
            }
        }
    }

}