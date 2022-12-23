package com.padc.kotlin.ftc.themovieapp.activities

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.tabs.TabLayout
import com.padc.kotlin.ftc.themovieapp.R
import com.padc.kotlin.ftc.themovieapp.adapters.BannerAdapter
import com.padc.kotlin.ftc.themovieapp.adapters.ShowcaseAdapter
import com.padc.kotlin.ftc.themovieapp.data.vos.ActorVO
import com.padc.kotlin.ftc.themovieapp.data.vos.GenreVO
import com.padc.kotlin.ftc.themovieapp.data.vos.MovieVO
import com.padc.kotlin.ftc.themovieapp.mvp.presenters.MainPresenter
import com.padc.kotlin.ftc.themovieapp.mvp.presenters.MainPresenterImpl
import com.padc.kotlin.ftc.themovieapp.mvp.views.MainView
import com.padc.kotlin.ftc.themovieapp.routers.navigateToMovieDetailActivity
import com.padc.kotlin.ftc.themovieapp.routers.navigateToMovieSearchActivity
import com.padc.kotlin.ftc.themovieapp.viewpods.ActorListViewPod
import com.padc.kotlin.ftc.themovieapp.viewpods.MovieListViewPod
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(), MainView {

    lateinit var mBannerAdapter: BannerAdapter
    lateinit var mShowcaseAdapter: ShowcaseAdapter
    lateinit var mBestPopularMovieListViewPod: MovieListViewPod
    lateinit var mMoviesByGenreViewPod: MovieListViewPod
    lateinit var mActorListViewPod: ActorListViewPod

    // Model
    // State

    // Presenter
    private lateinit var mPresenter: MainPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setUpPresenter()

        setUpActionBar()
        setUpViewPods()
        setUpBannerViewPager()
        setUpShowCaseRecyclerView()

        setUpListeners()

        mPresenter.onUiReady(this)

        //requestData()
    }

    private fun setUpPresenter() {
        mPresenter = ViewModelProvider(this)[MainPresenterImpl::class.java]
        mPresenter.initView(this)
    }

    override fun showNowPlayingMovies(nowPlayingMovies: List<MovieVO>) {
        mBannerAdapter.setNewData(nowPlayingMovies)
    }

    override fun showTopRatedMovies(topRatedMovies: List<MovieVO>) {
        mShowcaseAdapter.setNewData(topRatedMovies)
    }

    override fun showPopularMovies(popularMovies: List<MovieVO>) {
        mBestPopularMovieListViewPod.setData(popularMovies)
    }

    override fun showGenres(genreList: List<GenreVO>) {
        setUpGenreTabLayout(genres = genreList)
    }

    override fun showMoviesByGenre(nowPlayingMovies: List<MovieVO>) {
        mMoviesByGenreViewPod.setData(nowPlayingMovies)
    }

    override fun showActors(actors: List<ActorVO>) {
        mActorListViewPod.setData(actors)
    }

    override fun navigateToMovieDetailScreen(movieId: Int) {
        navigateToMovieDetailActivity(movieId = movieId)
    }

    override fun showError(errorString: String) {
        Toast.makeText(this, errorString, Toast.LENGTH_SHORT).show()
    }

    private fun setUpViewPods() {
        mBestPopularMovieListViewPod = vpBestPopularMovieList as MovieListViewPod
        mBestPopularMovieListViewPod.setUpMovieListViewPod(mPresenter)

        mMoviesByGenreViewPod = vpMoviesByGenre as MovieListViewPod
        mMoviesByGenreViewPod.setUpMovieListViewPod(mPresenter)

        mActorListViewPod = vpActorList as ActorListViewPod

    }

    private fun setUpShowCaseRecyclerView() {
        mShowcaseAdapter = ShowcaseAdapter(mPresenter)
        rvShowcases.adapter = mShowcaseAdapter
        rvShowcases.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
    }

    private fun setUpListeners() {
        tabLayoutGenre.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                mPresenter.onTapGenre(tab?.position ?: 0)
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

        mBannerAdapter = BannerAdapter(mPresenter)
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

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        //Snackbar.make(window.decorView, item.itemId.toString(), Snackbar.LENGTH_SHORT).show()
        when (item.itemId) {
            R.id.itemSearch -> {
                navigateToMovieSearchActivity()
            }
            else -> super.onOptionsItemSelected(item)
        }
        return super.onOptionsItemSelected(item)
    }

}