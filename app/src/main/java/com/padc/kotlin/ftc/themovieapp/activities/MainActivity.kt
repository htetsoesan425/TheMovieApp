package com.padc.kotlin.ftc.themovieapp.activities

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.tabs.TabLayout
import com.padc.kotlin.ftc.themovieapp.R
import com.padc.kotlin.ftc.themovieapp.adapters.BannerAdapter
import com.padc.kotlin.ftc.themovieapp.adapters.ShowcaseAdapter
import com.padc.kotlin.ftc.themovieapp.data.vos.GenreVO
import com.padc.kotlin.ftc.themovieapp.delegates.BannerViewHolderDelegate
import com.padc.kotlin.ftc.themovieapp.delegates.MovieViewHolderDelegate
import com.padc.kotlin.ftc.themovieapp.delegates.ShowCaseViewHolderDelegate
import com.padc.kotlin.ftc.themovieapp.mvvm.MainViewModel
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

    // View Model
    private lateinit var mViewModel: MainViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setUpViewModel()

        setUpActionBar()
        setUpViewPods()
        setUpBannerViewPager()
        setUpShowCaseRecyclerView()
        setUpListeners()

        observeLiveData()
        //requestData()
    }

    private fun setUpViewModel() {
        mViewModel = ViewModelProvider(this)[MainViewModel::class.java]
        mViewModel.getInitialData()
    }

    private fun observeLiveData() {
        mViewModel.nowPlayingMovieLiveData?.observe(this, mBannerAdapter::setNewData)
        mViewModel.popularMovieLiveData?.observe(this, mBestPopularMovieListViewPod::setData)
        mViewModel.topRatedMovieLiveData?.observe(this, mShowcaseAdapter::setNewData)
        mViewModel.genreLiveData.observe(this, this::setUpGenreTabLayout)
        mViewModel.moviesByGenreLiveData.observe(this, mMoviesByGenreViewPod::setData)
        mViewModel.actorsLiveData.observe(this, mActorListViewPod::setData)

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
                mViewModel.getMovieByGenre(tab?.position ?: 0)
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

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        //Snackbar.make(window.decorView, item.itemId.toString(), Snackbar.LENGTH_SHORT).show()
        when (item.itemId) {
            R.id.itemSearch -> {
                startActivity(MovieSearchActivity.newIntent(this))
            }
            else -> super.onOptionsItemSelected(item)
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onTapMovieFromBanner(movieId: Int) {
        Log.d("MainActivity", "onTapMovieFromBanner: $movieId")
        startActivity(MovieDetailActivity.newIntent(this, movieId = movieId))
    }

    override fun onTapMovieFromShowCase(movieId: Int) {
        startActivity(MovieDetailActivity.newIntent(this, movieId = movieId))
    }

    override fun onTapMovie(movieId: Int) {
        startActivity(MovieDetailActivity.newIntent(this, movieId = movieId))
    }
}