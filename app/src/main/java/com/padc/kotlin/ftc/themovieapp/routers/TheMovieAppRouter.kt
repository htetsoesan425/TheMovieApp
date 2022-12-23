package com.padc.kotlin.ftc.themovieapp.routers

import android.app.Activity
import com.padc.kotlin.ftc.themovieapp.activities.MovieDetailActivity
import com.padc.kotlin.ftc.themovieapp.activities.MovieSearchActivity

fun Activity.navigateToMovieDetailActivity(movieId: Int) {
    startActivity(MovieDetailActivity.newIntent(this, movieId = movieId))
}

fun Activity.navigateToMovieSearchActivity() {
    startActivity(MovieSearchActivity.newIntent(this))
}