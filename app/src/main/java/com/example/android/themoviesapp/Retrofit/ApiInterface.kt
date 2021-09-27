package com.example.android.themoviesapp.Retrofit

import com.example.android.themoviesapp.BuildConfig
import com.example.android.themoviesapp.Others.BASEURL
import com.example.android.themoviesapp.Others.MOVIES_API_KEY
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import java.util.ArrayList

interface ApiInterface {



    @GET("movie/upcoming?api_key=${MOVIES_API_KEY}")
    suspend fun getUpComingMovies() : Response<GetUpComingMoviesResponse>

    @GET("movie/{movieId}?api_key=${MOVIES_API_KEY}")
    suspend fun getMovieDetails(@Path("movieId") movieId:Long) :Response<GetMovieDetailsResponse>

    @GET("movie/{movieId}/images?api_key=${MOVIES_API_KEY}")
    suspend fun getMovieImages(@Path("movieId") movieId:Long):Response<GetMoviePostersResponse>

    @GET("movie/{movieId}/videos?api_key=${MOVIES_API_KEY}")
    suspend fun getMovieTrailerUrl(@Path("movieId") movieId:Long):Response<GetMovieTrailerUrlsResponse>


}

object MoviesDBClient{

    val api: ApiInterface

    init {

        var httpClient: OkHttpClient.Builder = OkHttpClient.Builder();
        httpClient.addInterceptor {
            val original: Request = it.request()
            val request = original.newBuilder()
                .header("Content-Type", "application/json")
                .method(original.method, original.body)
                .build()
            it.proceed(request)
        }

        /*
        * displaying requests and response in Logcat*/
        if(BuildConfig.DEBUG) {
            val interceptor = HttpLoggingInterceptor()
            interceptor.level = HttpLoggingInterceptor.Level.BODY
            httpClient.addInterceptor(interceptor)
        }


        val gson = GsonBuilder()
            .setLenient()
            .serializeNulls()
            .create()

        val retrofit = Retrofit.Builder()
            .client(httpClient.build())
            .baseUrl(BASEURL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
        api = retrofit.create(ApiInterface::class.java)
    }
}
