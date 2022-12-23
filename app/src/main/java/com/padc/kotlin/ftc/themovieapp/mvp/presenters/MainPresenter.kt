package com.padc.kotlin.ftc.themovieapp.mvp.presenters

import com.padc.kotlin.ftc.themovieapp.delegates.BannerViewHolderDelegate
import com.padc.kotlin.ftc.themovieapp.delegates.MovieViewHolderDelegate
import com.padc.kotlin.ftc.themovieapp.delegates.ShowCaseViewHolderDelegate
import com.padc.kotlin.ftc.themovieapp.mvp.views.MainView

interface MainPresenter : IBasePresenter, BannerViewHolderDelegate, ShowCaseViewHolderDelegate,
    MovieViewHolderDelegate {
    fun initView(view: MainView)
    fun onTapGenre(genrePosition: Int)
}