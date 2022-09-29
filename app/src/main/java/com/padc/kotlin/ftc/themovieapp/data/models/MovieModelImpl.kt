package com.padc.kotlin.ftc.themovieapp.data.models

import com.padc.kotlin.ftc.themovieapp.data.vos.*
import com.padc.kotlin.ftc.themovieapp.persistence.MovieDatabase
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers

object MovieModelImpl : MovieModel, BaseModel() {

//    private val mMovieDataAgent: MovieDataAgent = RetrofitDataAgentImpl   //remove dataAgent

    private var mMovieDatabase: MovieDatabase? = null

//    fun initDatabase(context: Context) {  //move to base
//        mMovieDatabase = MovieDatabase.getDBInstance(context)
//    }

    override fun getNowPlayingMovies(
        onSuccess: (List<MovieVO>) -> Unit,
        onFailure: (String) -> Unit
    ) {
        //persistence data - set up view layer
        onSuccess(mMovieDatabase?.movieDao()?.getAllMoviesByType(type = NOW_PLAYING) ?: listOf())

        //network data
        mTheMovieApi.getNowPlayingMovies(page = 1)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({

                //save to persistence layer
                it.results?.forEach { movie ->
                    movie.type = NOW_PLAYING
                }
                mMovieDatabase?.movieDao()?.insertMovies(it.results ?: listOf())

                //network data - set up view layer
                onSuccess(it.results ?: listOf())

            },
                {
                    onFailure(it.localizedMessage ?: "")
                }
            )
    }

    override fun getPopularMovies(
        onSuccess: (List<MovieVO>) -> Unit,
        onFailure: (String) -> Unit
    ) {
        //persistence data - set up view layer
        onSuccess(mMovieDatabase?.movieDao()?.getAllMoviesByType(type = POPULAR) ?: listOf())

        mTheMovieApi.getPopularMovies()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                it.results?.forEach { movie ->
                    movie.type = POPULAR
                }
                mMovieDatabase?.movieDao()?.insertMovies(it.results ?: listOf())
                onSuccess(it.results ?: listOf())
            },
                {
                    onFailure(it.localizedMessage ?: "")
                }
            )
    }

    override fun getTopRatedMovies(
        onSuccess: (List<MovieVO>) -> Unit,
        onFailure: (String) -> Unit
    ) {
        onSuccess(mMovieDatabase?.movieDao()?.getAllMoviesByType(type = TOP_RATED) ?: listOf())

        mTheMovieApi.getTopRatedMovies(page = 1)
            .subscribeOn(Schedulers.io())
            .subscribeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    it.results?.forEach { movie ->
                        movie.type = TOP_RATED
                    }
                    mMovieDatabase?.movieDao()?.insertMovies(it.results ?: listOf())
                    onSuccess(it.results ?: listOf())
                },
                {
                    onFailure(it.localizedMessage ?: "")
                }
            )
    }

    override fun getGenres(onSuccess: (List<GenreVO>) -> Unit, onFailure: (String) -> Unit) {
        mTheMovieApi.getGenres()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                onSuccess(it.genres)
            }, {
                onFailure(it.localizedMessage ?: "")
            })
    }

    override fun getMoviesByGenre(
        genreId: String,
        onSuccess: (List<MovieVO>) -> Unit,
        onFailure: (String) -> Unit
    ) {
        mTheMovieApi.getMoviesByGenre(genreId = genreId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                onSuccess(it.results ?: listOf())
            }, {
                onFailure(it.localizedMessage ?: "")
            }
            )
    }

    override fun getActors(
        onSuccess: (List<ActorVO>) -> Unit,
        onFailure: (String) -> Unit
    ) {
        mTheMovieApi.getActors().subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                onSuccess(it.result)
            },
                {
                    onFailure(it.localizedMessage ?: "")
                }
            )
    }

    override fun getMovieDetail(
        movieId: String,
        onSuccess: (MovieVO) -> Unit,
        onFailure: (String) -> Unit
    ) {
        val movieFromDataBase = mMovieDatabase?.movieDao()?.getMovieById(movieId = movieId.toInt())
        movieFromDataBase?.let {
            onSuccess(it)
        }

        //network data
        mTheMovieApi.getMovieDetail(movieId = movieId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({

                //get selected movie
                val movieFromDataBaseToSync =
                    mMovieDatabase?.movieDao()?.getMovieById(movieId = movieId.toInt())

                //set and save database's movie type to network's movie type
                it.type = movieFromDataBaseToSync?.type

                //save to persistence layer
                mMovieDatabase?.movieDao()?.insertSingleMovie(it)

                //set up to view layer
                onSuccess(it)
            },
                {
                    onFailure(it.localizedMessage ?: "")
                })
    }

    override fun getCreditsByMovie(
        movieId: String,
        onSuccess: (Pair<List<ActorVO>, List<ActorVO>>) -> Unit,
        onFailure: (String) -> Unit
    ) {
        mTheMovieApi.getCreditsByMovie(
            movieId = movieId
        )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                onSuccess(Pair(it.cast ?: listOf(), it.crew ?: listOf()))
            },
                {
                    onFailure(it.localizedMessage ?: "")
                }
            )
    }

}