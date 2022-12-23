package com.padc.kotlin.ftc.themovieapp.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.padc.kotlin.ftc.themovieapp.R
import com.padc.kotlin.ftc.themovieapp.data.vos.GenreVO
import com.padc.kotlin.ftc.themovieapp.data.vos.MovieVO
import com.padc.kotlin.ftc.themovieapp.mvvm.MovieDetailsViewModel
import com.padc.kotlin.ftc.themovieapp.utils.IMAGE_BASE_URL
import com.padc.kotlin.ftc.themovieapp.viewpods.ActorListViewPod
import kotlinx.android.synthetic.main.activity_movie_detail.*

class MovieDetailActivity : AppCompatActivity() {

    companion object {
        private const val EXTRA_MOVIE_ID = "EXTRA_MOVIE_ID"

        fun newIntent(context: Context, movieId: Int): Intent {
            val intent = Intent(context, MovieDetailActivity::class.java)
            intent.putExtra(EXTRA_MOVIE_ID, movieId)
            return intent
        }
    }

    //view pods
    lateinit var actorsViewPod: ActorListViewPod
    lateinit var creatorsViewPod: ActorListViewPod

    // view model
    private lateinit var mViewModel: MovieDetailsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_detail)

        setUpViewPods()
        setUpListener()

        val movieId = intent?.getIntExtra(EXTRA_MOVIE_ID, 0)

        //Snackbar.make(window.decorView, "movieId: $movieId", Snackbar.LENGTH_SHORT).show()

        movieId?.let {
            setUpViewModel(it)
        }

        observeLiveData()
    }

    private fun observeLiveData() {
        mViewModel.movieDetailsLiveData?.observe(this) {
            it?.let { movieVO ->
                bindData(movieVO)
            }
        }

        mViewModel.castLiveData.observe(this, actorsViewPod::setData)
        mViewModel.crewLiveData.observe(this, creatorsViewPod::setData)
    }

    private fun setUpViewModel(movieId: Int) {
        mViewModel = ViewModelProvider(this)[MovieDetailsViewModel::class.java]
        mViewModel.getInitialData(movieId = movieId)

    }

    private fun bindData(movie: MovieVO) {
        Glide.with(this)
            .load("$IMAGE_BASE_URL${movie.posterPath}")
            .into(ivMovieDetailImage)
        tvMovieName.text = movie.title
        collapsingToolbar.title = movie.title
        tvReleaseYear.text = movie.releaseDate?.substring(0, 4)
        tvMovieRating.text = movie.voteAverage?.toString() ?: ""

        movie.voteCount.let {
            tvNumberOfVotes.text = "$it VOTE"
        }

        rbMovieRating.rating = movie.getRatingBasedOnFiveStar()

        bindGenres(movie = movie, genres = movie.genres ?: listOf())

        tvOverview.text = movie.overview ?: ""
        tvOriginalTitle.text = movie.originalTitle ?: ""
        tvType.text = movie.getGenresAsCommaSeparatedString()
        tvProduction.text = movie.getProductionCountriesCommaSeparatedString()
        tvPremiere.text = movie.releaseDate ?: ""
        tvDescription.text = movie.overview ?: ""

    }

    private fun bindGenres(movie: MovieVO, genres: List<GenreVO>) {
        movie.genres?.count()?.let {
            tvFirstGenre.text = genres.firstOrNull()?.name ?: ""
            tvSecondGenre.text = genres.getOrNull(1)?.name ?: ""
            tvThirdGenre.text = genres.getOrNull(2)?.name ?: ""

            if (it < 3) {
                tvThirdGenre.visibility = View.GONE
            } else if (it < 2) {
                tvSecondGenre.visibility = View.GONE
            }
        }
    }

    private fun setUpListener() {
        btnBack.setOnClickListener {
            super.onBackPressed()
        }
    }

    private fun setUpViewPods() {
        actorsViewPod = vpActors as ActorListViewPod
        actorsViewPod.setUpViewActorViewPods(
            backgroundColorReference = R.color.colorPrimary,
            titleString = getString(R.string.lbl_actors),
            moreTitleText = ""
        )

        creatorsViewPod = vpCreators as ActorListViewPod
        creatorsViewPod.setUpViewActorViewPods(
            backgroundColorReference = R.color.colorPrimary,
            titleString = getString(R.string.lbl_creators),
            moreTitleText = getString(R.string.lbl_more_creators)
        )
    }
}