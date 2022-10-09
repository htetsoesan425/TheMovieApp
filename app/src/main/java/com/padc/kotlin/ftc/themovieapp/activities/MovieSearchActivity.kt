package com.padc.kotlin.ftc.themovieapp.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.jakewharton.rxbinding4.widget.textChanges
import com.padc.kotlin.ftc.themovieapp.R
import com.padc.kotlin.ftc.themovieapp.adapters.MovieAdapter
import com.padc.kotlin.ftc.themovieapp.data.models.MovieModelImpl
import com.padc.kotlin.ftc.themovieapp.delegates.MovieViewHolderDelegate
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_movie_search.*
import java.util.concurrent.TimeUnit

class MovieSearchActivity : AppCompatActivity(), MovieViewHolderDelegate {

    companion object {
        fun newIntent(context: Context): Intent {
            return Intent(context, MovieSearchActivity::class.java)
        }
    }

    //adapter
    private lateinit var mMovieAdapter: MovieAdapter

    //models
    private val mMovieModel = MovieModelImpl

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_search)

        setUpListeners()
        setUpRecyclerView()
    }

    private fun setUpRecyclerView() {
        mMovieAdapter = MovieAdapter(this)
        rvMovies.adapter = mMovieAdapter
        rvMovies.layoutManager = GridLayoutManager(this, 2)
    }

    private fun setUpListeners() {
        etSearch.textChanges()
            .debounce(500L, TimeUnit.MILLISECONDS)
            .flatMap {
                mMovieModel.searchMovie(it.toString())
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                mMovieAdapter.setNewData(it)
            }, {
                showError(it.localizedMessage ?: "")
            })
    }

    private fun showError(it: String) {
        Snackbar.make(window.decorView, it, Snackbar.LENGTH_SHORT).show()
    }

    override fun onTapMovie(movieId: Int) {

    }
}