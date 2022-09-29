package com.padc.kotlin.ftc.themovieapp.viewholders

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.padc.kotlin.ftc.themovieapp.data.vos.MovieVO
import com.padc.kotlin.ftc.themovieapp.delegates.ShowCaseViewHolderDelegate
import com.padc.kotlin.ftc.themovieapp.utils.IMAGE_BASE_URL
import kotlinx.android.synthetic.main.view_holder_show_case.view.*

class ShowcaseViewHolder(itemView: View, private val mDelegate: ShowCaseViewHolderDelegate) :
    RecyclerView.ViewHolder(itemView) {

    private var mMovie: MovieVO? = null

    init {
        itemView.setOnClickListener {

            //Log.d("TAG", "movieID: listen click")

            mMovie?.let { movie ->
                //Log.d("TAG", "movieID: ${movie.id}")
                mDelegate.onTapMovieFromShowCase(movie.id)
            }
        }
    }

    fun bindData(movie: MovieVO) {
        mMovie = movie

        Glide.with(itemView.context)
            .load("$IMAGE_BASE_URL${movie.posterPath}")
            .into(itemView.ivShowCase)
        itemView.tvShowCaseMovieName.text = movie.title
        itemView.tvShowCaseMovieDate.text = movie.releaseDate
    }

}