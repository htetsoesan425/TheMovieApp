package com.padc.kotlin.ftc.themovieapp.mvp.presenters

import android.util.Log
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import com.padc.kotlin.ftc.themovieapp.data.models.MovieModel
import com.padc.kotlin.ftc.themovieapp.data.models.MovieModelImpl
import com.padc.kotlin.ftc.themovieapp.data.vos.GenreVO
import com.padc.kotlin.ftc.themovieapp.mvp.views.MainView

class MainPresenterImpl : ViewModel(), MainPresenter {

    // View
    var mView: MainView? = null

    // Model
    private val mMovieModel: MovieModel = MovieModelImpl

    // State
    private var mGenres: List<GenreVO>? = null


    override fun initView(view: MainView) {
        mView = view
    }

    override fun onUiReady(owner: LifecycleOwner) {

        // now playing
        mMovieModel.getNowPlayingMovies {
            mView?.showError(it)
        }?.observe(owner) {
            mView?.showNowPlayingMovies(it)
        }

        // popular movies
        mMovieModel.getPopularMovies {
            mView?.showError(it)
        }?.observe(owner) {
            mView?.showPopularMovies(it)
        }

        //top rated movies
        mMovieModel.getTopRatedMovies {
            mView?.showError(it)

        }?.observe(owner) {
            mView?.showTopRatedMovies(it)
        }

        //genres
        mMovieModel.getGenres(
            onSuccess = {
                mGenres = it
                mView?.showGenres(it)
                it.firstOrNull()?.id?.let { firstGenreId -> onTapGenre(firstGenreId) }
            },
            onFailure = {
                mView?.showError(it)
            }
        )

        //actors
        mMovieModel.getActors(
            onSuccess = {
                Log.d("TAG", "requestData: $it")
                mView?.showActors(it)
            },
            onFailure = {
                mView?.showError(it)
            }
        )
    }

    override fun onTapMovieFromBanner(movieId: Int) {
        mView?.navigateToMovieDetailScreen(movieId = movieId)
    }

    override fun onTapMovie(movieId: Int) {
        mView?.navigateToMovieDetailScreen(movieId = movieId)
    }

    override fun onTapMovieFromShowCase(movieId: Int) {
        mView?.navigateToMovieDetailScreen(movieId = movieId)
    }

    override fun onTapGenre(genrePosition: Int) {
        mGenres?.getOrNull(genrePosition)?.id?.let { genreId ->
            mMovieModel.getMoviesByGenre(genreId = genreId.toString(), onSuccess = {
                mView?.showMoviesByGenre(it)
            },
                onFailure = {
                    mView?.showError(it)
                })


        }
    }
}