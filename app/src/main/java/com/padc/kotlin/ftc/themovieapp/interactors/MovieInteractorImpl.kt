package com.padc.kotlin.ftc.themovieapp.interactors

import androidx.lifecycle.LiveData
import com.padc.kotlin.ftc.themovieapp.data.models.MovieModel
import com.padc.kotlin.ftc.themovieapp.data.models.MovieModelImpl
import com.padc.kotlin.ftc.themovieapp.data.vos.ActorVO
import com.padc.kotlin.ftc.themovieapp.data.vos.GenreVO
import com.padc.kotlin.ftc.themovieapp.data.vos.MovieVO
import io.reactivex.rxjava3.core.Observable

object MovieInteractorImpl : MovieInteractor {

    // Model
    private val mMovieModel: MovieModel = MovieModelImpl

    override fun getNowPlayingMovies(onFailure: (String) -> Unit): LiveData<List<MovieVO>>? {
        return mMovieModel.getNowPlayingMovies(onFailure)
    }

    override fun getPopularMovies(onFailure: (String) -> Unit): LiveData<List<MovieVO>>? {
        return mMovieModel.getPopularMovies(onFailure)
    }

    override fun getTopRatedMovies(onFailure: (String) -> Unit): LiveData<List<MovieVO>>? {
        return mMovieModel.getTopRatedMovies(onFailure)
    }

    override fun getGenres(onSuccess: (List<GenreVO>) -> Unit, onFailure: (String) -> Unit) {
        return mMovieModel.getGenres(onSuccess = onSuccess, onFailure = onFailure)
    }

    override fun getMoviesByGenre(
        genreId: String,
        onSuccess: (List<MovieVO>) -> Unit,
        onFailure: (String) -> Unit
    ) {
        return mMovieModel.getMoviesByGenre(
            genreId = genreId,
            onSuccess = onSuccess,
            onFailure = onFailure
        )
    }

    override fun getActors(onSuccess: (List<ActorVO>) -> Unit, onFailure: (String) -> Unit) {
        return mMovieModel.getActors(
            onSuccess = onSuccess,
            onFailure = onFailure
        )
    }

    override fun getMovieDetail(movieId: String, onFailure: (String) -> Unit): LiveData<MovieVO?>? {
        return mMovieModel.getMovieDetail(movieId = movieId, onFailure = onFailure)
    }

    override fun getCreditsByMovie(
        movieId: String,
        onSuccess: (Pair<List<ActorVO>, List<ActorVO>>) -> Unit,
        onFailure: (String) -> Unit
    ) {
        return mMovieModel.getCreditsByMovie(
            movieId = movieId,
            onSuccess = onSuccess,
            onFailure = onFailure
        )

    }

    override fun searchMovie(query: String): Observable<List<MovieVO>>? {
        return mMovieModel.searchMovie(query = query)
    }
}