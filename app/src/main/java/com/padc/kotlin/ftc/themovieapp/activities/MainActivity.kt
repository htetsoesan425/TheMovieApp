package com.padc.kotlin.ftc.themovieapp.activities

import android.os.Bundle
import android.util.Log
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayout
import com.padc.kotlin.ftc.themovieapp.R
import com.padc.kotlin.ftc.themovieapp.adapters.BannerAdapter
import com.padc.kotlin.ftc.themovieapp.adapters.ShowcaseAdapter
import com.padc.kotlin.ftc.themovieapp.data.models.MovieModel
import com.padc.kotlin.ftc.themovieapp.data.models.MovieModelImpl
import com.padc.kotlin.ftc.themovieapp.data.vos.GenreVO
import com.padc.kotlin.ftc.themovieapp.delegates.BannerViewHolderDelegate
import com.padc.kotlin.ftc.themovieapp.delegates.MovieViewHolderDelegate
import com.padc.kotlin.ftc.themovieapp.delegates.ShowCaseViewHolderDelegate
import com.padc.kotlin.ftc.themovieapp.viewpods.ActorListViewPod
import com.padc.kotlin.ftc.themovieapp.viewpods.MovieListViewPod
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), BannerViewHolderDelegate, ShowCaseViewHolderDelegate,
    MovieViewHolderDelegate {

    lateinit var mBannerAdapter: BannerAdapter
    lateinit var mShowcaseAdapter: ShowcaseAdapter
    lateinit var mBestPopularMovieListViewPod: MovieListViewPod
    lateinit var mMoviesByGenreViewPod: MovieListViewPod
    lateinit var mActorListViewPod: ActorListViewPod

    //model
    private val mMovieModel: MovieModel = MovieModelImpl

    //data
    private var mGenres: List<GenreVO>? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setUpActionBar()
        setUpViewPods()
        setUpBannerViewPager()
        //setUpGenreTabLayout()
        setUpShowCaseRecyclerView()

        setUpListeners()

        //MovieDataAgentImpl.getNowPlayingMovie()
        //OkHttpDataAgentImpl.getNowPlayingMovie()
        //RetrofitDataAgentImpl.getNowPlayingMovie()

        requestData()
    }

    private fun requestData() {
        //now playing movie
        mMovieModel.getNowPlayingMovies(
            onSuccess = {
                mBannerAdapter.setNewData(it)
            },
            onFailure = {
                showError(it)
            }
        )

        //popular movies
        mMovieModel.getPopularMovies(
            onSuccess = {
                mBestPopularMovieListViewPod.setData(it)
            },
            onFailure = {
                showError(it)
            }
        )

        //top rated movies
        mMovieModel.getTopRatedMovies(
            onSuccess = {
                mShowcaseAdapter.setNewData(it)
            },
            onFailure = {
                showError(it)
            }
        )

        //genres
        mMovieModel.getGenres(
            onSuccess = {
                mGenres = it

                setUpGenreTabLayout(it)

                it.firstOrNull()?.id?.let { genreId ->
                    getMoviesByGenre(genreId)
                }
            },
            onFailure = {
                showError(it)
            }
        )

        //actors
        mMovieModel.getActors(
            onSuccess = {
                Log.d("TAG", "requestData: $it")
                mActorListViewPod.setData(it)
            },
            onFailure = {
                showError(it)
            }
        )
    }

    private fun getMoviesByGenre(genreId: Int) {

        Log.d("TAG", "getMoviesByGenre: genreId:$genreId")
        mMovieModel.getMoviesByGenre(
            genreId = genreId.toString(),
            onSuccess = {

                Log.d("TAG", "getMoviesByGenre: MovieVO:$it")
                mMoviesByGenreViewPod.setData(it)
            },

            onFailure = {
                showError(it)
            })
    }

    private fun showError(it: String) {
        Snackbar.make(window.decorView, it, Snackbar.LENGTH_SHORT).show()
    }

    private fun setUpViewPods() {
        mBestPopularMovieListViewPod = vpBestPopularMovieList as MovieListViewPod
        mBestPopularMovieListViewPod.setUpMovieListViewPod(this)

        mMoviesByGenreViewPod = vpMoviesByGenre as MovieListViewPod
        mMoviesByGenreViewPod.setUpMovieListViewPod(this)

        mActorListViewPod = vpActorList as ActorListViewPod

    }

    private fun setUpShowCaseRecyclerView() {
        mShowcaseAdapter = ShowcaseAdapter(this)
        rvShowcases.adapter = mShowcaseAdapter
        rvShowcases.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
    }

    private fun setUpListeners() {
        tabLayoutGenre.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {

                //Snackbar.make(window.decorView, tab?.text ?: "", Snackbar.LENGTH_SHORT).show()

                mGenres?.getOrNull(tab?.position ?: 0)?.id?.let {
                    getMoviesByGenre(it)
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }

        })

    }

    private fun setUpGenreTabLayout(genres: List<GenreVO>) {
        genres.forEach {
            tabLayoutGenre.newTab().apply {
                text = it.name
                tabLayoutGenre.addTab(this)
            }
        }
    }

    private fun setUpBannerViewPager() {

        mBannerAdapter = BannerAdapter(this)
        viewPagerBanner.adapter = mBannerAdapter

        //combine with viewPager and dots indicator
        dotsIndicator.attachTo(viewPagerBanner)

    }

    private fun setUpActionBar() {
        setSupportActionBar(toolBar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_menu)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_discover, menu)
        return true
    }

    override fun onTapMovieFromBanner(movieId: Int) {
        Log.d("MainActivity", "onTapMovieFromBanner: $movieId")
        startActivity(MovieDetailActivity.newIntent(this, movieId = movieId))
    }

    override fun onTapMovieFromShowCase(movieId: Int) {
        Log.d("MainActivity", "onTapMovieFromShowCase: $movieId")
        startActivity(MovieDetailActivity.newIntent(this, movieId = movieId))
    }

    override fun onTapMovie(movieId: Int) {
        Log.d("MainActivity", "onTapMovie: $movieId")
        startActivity(MovieDetailActivity.newIntent(this, movieId = movieId))
    }
}