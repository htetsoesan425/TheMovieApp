package com.padc.kotlin.ftc.themovieapp.mvp.views

import com.padc.kotlin.ftc.themovieapp.data.vos.ActorVO
import com.padc.kotlin.ftc.themovieapp.data.vos.GenreVO
import com.padc.kotlin.ftc.themovieapp.data.vos.MovieVO

interface MainView : BaseView {
    fun showNowPlayingMovies(nowPlayingMovies: List<MovieVO>)
    fun showTopRatedMovies(topRatedMovies: List<MovieVO>)
    fun showPopularMovies(popularMovies: List<MovieVO>)
    fun showGenres(genreList: List<GenreVO>)
    fun showMoviesByGenre(nowPlayingMovies: List<MovieVO>)
    fun showActors(actors: List<ActorVO>)
    fun navigateToMovieDetailScreen(movieId: Int)
}