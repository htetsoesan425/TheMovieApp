package com.padc.kotlin.ftc.themovieapp.mvvm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.padc.kotlin.ftc.themovieapp.data.models.MovieModel
import com.padc.kotlin.ftc.themovieapp.data.models.MovieModelImpl
import com.padc.kotlin.ftc.themovieapp.data.vos.ActorVO
import com.padc.kotlin.ftc.themovieapp.data.vos.GenreVO
import com.padc.kotlin.ftc.themovieapp.data.vos.MovieVO

class MainViewModel : ViewModel() {

    // Model
    private val mMovieModel: MovieModel = MovieModelImpl

    // Live Data
    var nowPlayingMovieLiveData: LiveData<List<MovieVO>>? = null
    var popularMovieLiveData: LiveData<List<MovieVO>>? = null
    var topRatedMovieLiveData: LiveData<List<MovieVO>>? = null
    var genreLiveData = MutableLiveData<List<GenreVO>>()
    var moviesByGenreLiveData = MutableLiveData<List<MovieVO>>()
    var actorsLiveData = MutableLiveData<List<ActorVO>>()
    var mErrorLiveData = MutableLiveData<String>()

    fun getInitialData() {
        nowPlayingMovieLiveData = mMovieModel.getNowPlayingMovies { mErrorLiveData.postValue(it) }
        popularMovieLiveData = mMovieModel.getPopularMovies { mErrorLiveData.postValue(it) }
        topRatedMovieLiveData = mMovieModel.getTopRatedMovies { mErrorLiveData.postValue(it) }

        mMovieModel.getGenres(
            onSuccess = {
                genreLiveData.postValue(it)
                getMovieByGenre(0)
            }, onFailure = {
                mErrorLiveData.postValue(it)
            })

    }

    fun getMovieByGenre(genrePosition: Int) {
        genreLiveData.value?.getOrNull(genrePosition)?.id?.let {
            mMovieModel.getMoviesByGenre(
                genreId = it.toString(),
                onSuccess = { moviesByGenre ->
                    moviesByGenreLiveData.postValue(moviesByGenre)
                }, onFailure = { errorMsg ->
                    mErrorLiveData.postValue(errorMsg)
                })
        }
    }

}