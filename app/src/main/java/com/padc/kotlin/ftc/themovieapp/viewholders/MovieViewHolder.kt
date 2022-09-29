package com.padc.kotlin.ftc.themovieapp.viewholders

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.padc.kotlin.ftc.themovieapp.data.vos.MovieVO
import com.padc.kotlin.ftc.themovieapp.delegates.MovieViewHolderDelegate
import com.padc.kotlin.ftc.themovieapp.utils.IMAGE_BASE_URL
import kotlinx.android.synthetic.main.view_holder_movie.view.*

class MovieViewHolder(itemView: View, private val mDelegate: MovieViewHolderDelegate) :
    RecyclerView.ViewHolder(itemView) {

    private var mMovie: MovieVO? = null

    init {
        itemView.setOnClickListener {
            mMovie?.let { movie ->
                mDelegate.onTapMovie(movie.id)
            }
        }
    }

    fun bindData(movie: MovieVO) {

        mMovie = movie

        Glide.with(itemView.context)
            .load("$IMAGE_BASE_URL${movie.posterPath}")
            .into(itemView.ivMovieImage)
        itemView.tvMovieName.text = movie.title
        itemView.tvRating.text = movie.voteAverage.toString()
        itemView.rbMovieRating.rating = movie.getRatingBasedOnFiveStar()
    }

}