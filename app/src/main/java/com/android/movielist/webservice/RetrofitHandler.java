package com.android.movielist.webservice;

import com.android.movielist.webservice.apiservices.MovieApiService;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitHandler {

    private Retrofit retrofit;
    private MovieApiService movieApiService;

    public RetrofitHandler() {
        retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public MovieApiService getMovieApiService() {
        if (movieApiService == null)
            movieApiService = retrofit.create(MovieApiService.class);

        return movieApiService;
    }
}
