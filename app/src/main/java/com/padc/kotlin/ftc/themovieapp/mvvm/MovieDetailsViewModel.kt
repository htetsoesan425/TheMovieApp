package com.padc.kotlin.ftc.themovieapp.mvvm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.padc.kotlin.ftc.themovieapp.data.models.MovieModel
import com.padc.kotlin.ftc.themovieapp.data.models.MovieModelImpl
import com.padc.kotlin.ftc.themovieapp.data.vos.ActorVO
import com.padc.kotlin.ftc.themovieapp.data.vos.MovieVO

class MovieDetailsViewModel : ViewModel() {

    // Model
    private val mMovieModel: MovieModel = MovieModelImpl

    // Live Data
    var movieDetailsLiveData: LiveData<MovieVO?>? = null
    var castLiveData = MutableLiveData<List<ActorVO>>()
    var crewLiveData = MutableLiveData<List<ActorVO>>()
    var mErrorLiveData = MutableLiveData<String>()

    fun getInitialData(movieId: Int) {
        movieDetailsLiveData = mMovieModel.getMovieDetail(
            movieId = movieId.toString(),
            onFailure = {
                mErrorLiveData.postValue(it)
            }
        )

        mMovieModel.getCreditsByMovie(
            movieId = movieId.toString(),
            onSuccess = {
                castLiveData.postValue(it.first ?: listOf())
                crewLiveData.postValue(it.second ?: listOf())
            },
            onFailure = {
                mErrorLiveData.postValue(it)
            }

        )
    }
}