package com.padc.kotlin.ftc.themovieapp.network.dataagents

/*object OkHttpDataAgentImpl : MovieDataAgent {

    private val mClient: OkHttpClient = OkHttpClient.Builder()
        .connectTimeout(15, TimeUnit.SECONDS)
        .readTimeout(15, TimeUnit.SECONDS)
        .writeTimeout(15, TimeUnit.SECONDS)
        .build()

    override fun getNowPlayingMovie() {
        GetNowPlayingMovieTask(mClient).execute()
    }

    class GetNowPlayingMovieTask(private val mOkHttpClient: OkHttpClient) :
        AsyncTask<Void, Void, MovieListResponse>() {

        override fun doInBackground(vararg p0: Void?): MovieListResponse? {
            val request = Request.Builder()
                .url("""$BASE_URL$API_GET_NOW_PLAYING?api_key=$MOVIE_API_KEY&language=en-US&page=1""")
                .build()

            try {
                val response = mOkHttpClient.newCall(request).execute()

                if (response.isSuccessful) {
                    response.body?.let {
                        val responseString = it.string()
                        val movieListResponse = Gson().fromJson(
                            responseString,
                            MovieListResponse::class.java
                        )

                        return movieListResponse
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return null
        }

        override fun onPostExecute(result: MovieListResponse?) {
            super.onPostExecute(result)
        }

    }
}*/
