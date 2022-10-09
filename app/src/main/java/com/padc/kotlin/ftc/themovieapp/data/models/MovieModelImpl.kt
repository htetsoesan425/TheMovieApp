package com.padc.kotlin.ftc.themovieapp.data.models

import android.util.Log
import androidx.lifecycle.LiveData
import com.padc.kotlin.ftc.themovieapp.data.vos.*
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers

object MovieModelImpl : BaseModel(), MovieModel {

//    private val mMovieDataAgent: MovieDataAgent = RetrofitDataAgentImpl   //remove dataAgent

//    fun initDatabase(context: Context) {  //move to base model
//        mMovieDatabase = MovieDatabase.getDBInstance(context)
//    }

    override fun getNowPlayingMovies(
        onFailure: (String) -> Unit
    ): LiveData<List<MovieVO>>? {

        //network data
        mTheMovieApi.getNowPlayingMovies(page = 1)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                Log.d("TAG", "getNowPlayingMovies: $it")
                //save to persistence layer
                it.results?.forEach { movie ->
                    movie.type = NOW_PLAYING
                    mTheMovieDatabase?.movieDao()?.insertMovies(it.results)
                }

                //network data - set up view layer
                //onSuccess(it.results ?: listOf())

            }, {
                onFailure(it.localizedMessage ?: "")
            })

        return mTheMovieDatabase?.movieDao()?.getAllMoviesByType(type = NOW_PLAYING)
    }

    override fun getPopularMovies(
        onFailure: (String) -> Unit
    ): LiveData<List<MovieVO>>? {

        //persistence data - set up view layer
        //onSuccess(mMovieDatabase?.movieDao()?.getAllMoviesByType(type = POPULAR) ?: listOf())

        mTheMovieApi.getPopularMovies()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                it.results?.forEach { movie ->
                    movie.type = POPULAR
                    mTheMovieDatabase?.movieDao()?.insertMovies(it.results)
                }
                //onSuccess(it.results ?: listOf())
            }, {
                onFailure(it.localizedMessage ?: "")
            })
        return mTheMovieDatabase?.movieDao()?.getAllMoviesByType(type = POPULAR)
    }

    override fun getTopRatedMovies(
        onFailure: (String) -> Unit
    ): LiveData<List<MovieVO>>? {

        mTheMovieApi.getTopRatedMovies(page = 1)
            .subscribeOn(Schedulers.io())
            .subscribeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    it.results?.forEach { movie ->
                        movie.type = TOP_RATED
                        mTheMovieDatabase?.movieDao()?.insertMovies(it.results)
                    }
                    //onSuccess(it.results ?: listOf())
                }, {
                    onFailure(it.localizedMessage ?: "")
                }
            )
        return mTheMovieDatabase?.movieDao()?.getAllMoviesByType(type = TOP_RATED)
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
            })
    }

    override fun getActors(
        onSuccess: (List<ActorVO>) -> Unit,
        onFailure: (String) -> Unit
    ) {
        mTheMovieApi.getActors().subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                onSuccess(it.result)
            }, {
                onFailure(it.localizedMessage ?: "")
            })
    }

    override fun getMovieDetail(
        movieId: String,
        onFailure: (String) -> Unit
    ): LiveData<MovieVO?>? {

        //db data
/*        val movieFromDataBase = mMovieDatabase?.movieDao()?.getMovieById(movieId = movieId.toInt())
        movieFromDataBase?.let {
            onSuccess(it)
        }*/

        //network data
        mTheMovieApi.getMovieDetail(movieId = movieId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({

                //get selected movie
                val movieFromDataBaseToSync =
                    mTheMovieDatabase?.movieDao()?.getMovieByIdOneTime(movieId = movieId.toInt())

                //set and save database's movie type to network's movie type
                it.type = movieFromDataBaseToSync?.type

                //save to persistence layer
                mTheMovieDatabase?.movieDao()?.insertSingleMovie(it)

                //set up to view layer
                //onSuccess(it)
            }, {
                onFailure(it.localizedMessage ?: "")
            })


        return mTheMovieDatabase?.movieDao()?.getMovieById(movieId = movieId.toInt())
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
            }, {
                onFailure(it.localizedMessage ?: "")
            })
    }

    override fun searchMovie(query: String): Observable<List<MovieVO>> {
        return mTheMovieApi
            .searchMovie(query = query)
            .map { it.results ?: listOf() }
            .onErrorResumeNext { Observable.just(listOf()) }
            .subscribeOn(Schedulers.io())
    }

}