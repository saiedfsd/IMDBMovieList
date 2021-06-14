package com.android.movielist.di.modules;

import com.android.movielist.di.scops.AppScope;
import com.android.movielist.utilities.NetworkStateReceiver;
import com.android.movielist.webservice.Constants;
import com.android.movielist.webservice.apiservices.MovieApiService;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class NetworkModule {

    @AppScope
    @Provides
    public MovieApiService provideMovieApi(Retrofit retrofit) {
        return retrofit.create(MovieApiService.class);
    }

    @AppScope
    @Provides
    public Retrofit provideRetrofit(OkHttpClient okHttpClient) {
        return new Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(Constants.BASE_URL)
                .client(okHttpClient)
                .build();
    }

    @AppScope
    @Provides
    public OkHttpClient provideOkHttpClient(Interceptor interceptor) {

        return new OkHttpClient.Builder()
                .followRedirects(true)
                .addInterceptor(interceptor)
                .build();
    }


    @AppScope
    @Provides
    public Interceptor provideInterceptor() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        return interceptor;
    }

    @AppScope
    @Provides
    public NetworkStateReceiver provideNetworkStateReceiver(){
        return new NetworkStateReceiver();
    }
}
