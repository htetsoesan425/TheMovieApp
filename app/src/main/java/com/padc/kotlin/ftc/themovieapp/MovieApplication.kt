package com.padc.kotlin.ftc.themovieapp

import android.app.Application
import com.padc.kotlin.ftc.themovieapp.data.models.MovieModelImpl

class MovieApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        MovieModelImpl.initDatabase(applicationContext)
    }
}