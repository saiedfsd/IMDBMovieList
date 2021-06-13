package com.android.movielist.di.modules;

import com.android.movielist.activities.MovieListActivity;
import com.android.movielist.db.dao.GenreDao;
import com.android.movielist.db.dao.ImagesDao;
import com.android.movielist.db.dao.MovieDao;
import com.android.movielist.db.dao.PosterDao;
import com.android.movielist.di.scops.MovieListActivityScope;
import com.android.movielist.modelimpl.MovieListModelImpl;
import com.android.movielist.models.MovieListModel;
import com.android.movielist.mvpcontracts.MovieListActivityContract;
import com.android.movielist.presenters.IMovieListPresenter;
import com.android.movielist.presentersimpl.MovieListPresenterImpl;
import com.android.movielist.views.MovieListView;
import com.android.movielist.webservice.apiservices.MovieApiService;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

@Module
public class MovieListModule {

    public MovieListModule() {

    }

    @Provides
    public MovieListActivityContract.Presenter provideMovieListPresenter(MovieListActivityContract.View movieListView,
                                                                         MovieListActivityContract.Model movieListModel){
        return new MovieListPresenterImpl(movieListView,movieListModel);
    }

    @Provides
    public MovieListActivityContract.Model provideMovieListModel(GenreDao genreDao,
                                                ImagesDao imagesDao,
                                                MovieDao movieDao,
                                                PosterDao posterDao,
                                                MovieApiService movieApiService){

        return new MovieListModelImpl(genreDao, imagesDao, movieDao, posterDao, movieApiService);
    }

}
