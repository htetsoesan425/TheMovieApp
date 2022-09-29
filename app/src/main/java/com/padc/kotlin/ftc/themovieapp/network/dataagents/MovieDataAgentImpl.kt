package com.padc.kotlin.ftc.themovieapp.network.dataagents

/*
object MovieDataAgentImpl : MovieDataAgent {
    override fun getNowPlayingMovie() {
        GetNowPlayingMovieTask().execute()
    }

    class GetNowPlayingMovieTask() : AsyncTask<Void, Void, MovieListResponse?>() {
        override fun doInBackground(vararg p0: Void?): MovieListResponse? {

            val url: URL
            var reader: BufferedReader? = null
            val stringBuilder: StringBuilder
            try {

                url =
                    URL("""$BASE_URL$API_GET_NOW_PLAYING?api_key=$MOVIE_API_KEY&language=en-US&page=1""") //1

                val connection = url.openConnection() as HttpURLConnection  //2

                //set HTTP method
                connection.requestMethod = "GET"    //3

                //give it 15 sec for respond
                connection.readTimeout = 15 * 1000  //4 ms

                connection.doInput = true   //5

                connection.doOutput = false // Path, Query param => false, Body param => true

                connection.connect()    //7

                reader = BufferedReader(InputStreamReader(connection.inputStream))  //8

                stringBuilder = StringBuilder()

                //read line by line from raw json and append
                for (line in reader.readLines()) {
                    stringBuilder.append(line + "\n")
                }

                val responseString = stringBuilder.toString()
                Log.d("NowPlayingMovie", responseString)

                val movieListResponse = Gson().fromJson(
                    responseString,
                    MovieListResponse::class.java
                )

                return movieListResponse

            } catch (e: Exception) {
                e.printStackTrace()
                Log.e("NewsError", e.message ?: "")

            } finally {
                if (reader != null) {
                    try {
                        reader.close()
                    } catch (ioe: IOException) {
                        ioe.printStackTrace()
                    }

                }
            }
            return null
        }

        override fun onPostExecute(result: MovieListResponse?) {
            super.onPostExecute(result)
        }

    }
}*/
